class Test {

    Hello h;
    Hello[] h_array;
    String s$;

    void sing(String s) {

        int x = 2;
        int y = 1;
        while (h.isFriendly(h.z)) {
            h_array[x+y] = new Hello();
        }

        if (x == 5) {
            x = 5;
        }
    }
}

class Hello {
    public int z;

    public boolean isFriendly(int x) {
        if (x > 3) {
            return true;
        } else {
            return false;
        }
    }
}
