class Test {

    /**
     * Nested for loops
     */

    public static void main(String[] args) {

        for (int i = 0; i < 6; i += 1) {
            for (int j = 0; j < i; j += 1) {
                for (int k = 0; k < j; k += 1) {
                    System.out.println(i+j+k);
                }
            }
        }

    }
}