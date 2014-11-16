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
        if (index < maxSize) {
            list[index] = fic;
            index++;
            //If I have time, it's probably best to wait until the array is full before sorting,
            //or just create a separate insertion helper method
            Arrays.sort(list);
            return true;
        }

        //Fic is less popular than everything already in full array. Discard
        if (fic.compareTo(list[maxSize - 1]) <=0 ) {
            return false;
        }

        int location = index;
        while (fic.compareTo(list[index]) > 0 && location > 0) {
            list[location] = list[location - 1];
            location--;
        }

        list[location] = fic;

        return  true;
    }

    //self-explanatory
    public int size() {
        return index;
    }

    public Fanfic get(int i) {
        return list[i];
    }
}
