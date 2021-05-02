class Test {

    /**
     * Simple method overloading
     */

    public static int x() {
        return 1;
    }

    public int x(int n) {
        return 1 + n;
    }

    public static void main(String[] args) {



        Test t = new Test();

        t.run();

        x();
        t.x(5);

        System.out.println(t.x(5));
    }

    public void run() {
        this.x(4);
    }
}
