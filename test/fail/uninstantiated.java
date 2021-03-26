class Test {
    A[] a;
    private int[] z;

    void sing() {
        a = null;
    }

    private static void slurp(int x) {
        int y = 1;
    }

    void test() {
        int t = this.z;
        this.sing();
        slurp(z[2]);
    }

    void accessStaticFields() {
        int z = A.st1 + A.st2;
    }

    void accessStaticMethods() {
        A.st1();
    }
}

class A {
    public int x;
    public int y;
    private int z;

    public static int st1;
    public static int st2;

    public int hello() {
        z = 3;
        return 3;
    }

    /*
    public static void st1() {
        st1 = 4;
    } */
}
