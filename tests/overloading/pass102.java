class Test {
    /**
     * overloading in separate classes
     */

    public static void main(String[] args) {
        A.doStuff();
        A.doStuff(true, 15);
        A.doStuff(2);
        System.out.println(A.doStuff(true, 1));     // >>> -1
    }
}

class A {
    public static int doStuff(boolean b, int x) {
        if (b) return -x; else return x + 1;
    }

    public static int doStuff(int x) {
        return x+17;
    }

    public static void doStuff() {
        // actually do no stuff.
    }
}
