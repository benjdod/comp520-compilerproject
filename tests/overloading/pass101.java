class Test {

    /**
     * Simple method overloading
     */

    public static int x() {
        return 1;
    }

    public static int x(int n) {
        return 1 + n;
    }

    public static void main(String[] args) {

        System.out.println(x());        // >>> 1
        System.out.println(x(1));       // >>> 2
    }
}
