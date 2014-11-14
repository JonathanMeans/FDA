package fda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jwsm on 12/11/14.
 */
//This would be better as a heap, if there's time
public class BoundedSortedFics {
    private Fanfic[] list;
    int maxSize;
    int index = 0;

    public BoundedSortedFics(int maxSize) {
        this.maxSize = maxSize;
        list = new Fanfic[maxSize];
    }

    public boolean insert(Fanfic fic) {
        if (index < maxSize) {
            list[index] = fic;
            index++;
            return true;
        }

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

    public int size() {
        return index;
    }

    public Fanfic get(int i) {
        return list[i];
    }
}
