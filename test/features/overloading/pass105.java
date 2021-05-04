class Test {

    /**
     * Overloading main (bad idea...)
     */
    public static void main(String[] args) {
        main();
    }

    public static void main() {
        System.out.println("I'm the real main method!");
    }
}
