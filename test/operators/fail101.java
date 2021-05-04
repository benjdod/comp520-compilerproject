class Test {

    /**
     * Bad ternary expression (unterminated outer expression)
     */
    public static void main(String[] args) {
        int x = true ? 5 ? 3 : 1;
    }
}