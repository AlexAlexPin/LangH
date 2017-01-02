package com.pinin.alex.data;

class CheckValueHelper {

    static void checkNull(Object o) throws IllegalArgumentException {
        if (o == null) throw new NullPointerException("Value can not be null");
    }

    static void checkNull(Object... os) throws IllegalArgumentException {
        for (Object o : os) checkNull(o);
    }

    static void checkSize(int index, int size) throws IllegalArgumentException {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Size: " + size + ", index: " + index + " is out of bounds");
    }
}
