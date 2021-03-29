class Test {
    A a;
    private int[] z;

    void test() {
        int a = this.a.y;
        a = A.y;
    }
}

class A {
    public static int x;
    public int y; 
}
