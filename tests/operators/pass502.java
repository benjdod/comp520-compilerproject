class Test {

    /**
     * Ternary expression
     */

    private static boolean yes_okay;

    public static boolean yes() {
        yes_okay = ! yes_okay;
        return yes_okay;
    }

    public static int go() {
        return yes() ? 2 : -5;
    }

    public static void main(String[] args) {
        go();
        go();
        System.out.println(go());        // >>> -5
    }
}
