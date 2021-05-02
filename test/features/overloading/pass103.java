class Test {

    /**
     * Proper separation between overloaded methods and fields
     * with the same name
     * @param args
     */
    public int x;
    public int x() {
        return this.x;
    }

    public int x(int y) {
        return this.x + y;
    }

    public static void main(String[] args) {
        Test t = new Test();

        t.x = 5;

        System.out.println(t.x() + t.x(-5));        // >>> 5
    }
}
