package fda;

import java.util.Arrays;

/**
 * Class for easy storage and maintenance of top Fanfics
 */
//This would be better as a heap, if there's time
public class BoundedSortedFics {
    private Fanfic[] list;
    private int maxSize;
    private int index = 0;

    public BoundedSortedFics(int maxSize) {
        this.maxSize = maxSize;
        list = new Fanfic[maxSize];
    }

    //Insert an Fanfic into its proper order, if applicable
    //Could be done in O(lg n), I believe, but the array should never grow excessively long,
    //premature optimization, is evil, I don't want to build a heap right now, etc.
    //Nothing wrong with linear time.
    public boolean insert(Fanfic fic) {
        //this strategy isn't going to work when there are less than the overflow number number of fics
        //that's unlikely to happen, though, so we'll leave it for now.
        if (index == 0) {
            list[index] = fic;
            index++;
            return true;
        }

        if (index < maxSize) {
            actuallyInsert(fic);
            index++;
            return true;
        }

        //Fic is less popular than everything already in full array. Discard
        if (fic.compareTo(list[maxSize - 1]) < 0 ) {
            return false;
        }

        actuallyInsert(fic);

        return  true;
    }

    private void actuallyInsert(Fanfic fic) {
        int location = Math.min(index, list.length - 1);

        while (location > 0 && fic.compareTo(list[location - 1]) >= 0) {
            list[location] = list[location - 1];
            location--;
        }

        list[location] = fic;
    }

    //self-explanatory
    public int size() {
        return Math.min(index, maxSize);
    }

    public Fanfic get(int i) {
        return list[i];
    }
}
