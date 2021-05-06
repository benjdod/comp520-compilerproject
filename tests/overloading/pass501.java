class Test {

    /**
     * Simple method overloading
     */

    public static int x() {
        return 2;
    }

    public static int x(int n) {
        return -n;
    }

    public static void main(String[] args) {

        System.out.println(x() + x(1));       
    }
}
