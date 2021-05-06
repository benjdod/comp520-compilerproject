class Test {

    /**
     * Nested for loops
     */

    public static void main(String[] args) {

        int x = 0;

        for (int i = 1; i < 2; i += 1) {
            x += i;
            for (int j = 1; j < 2; j += 1) {
                x += j;
                for (int k = 1; k < 2; k += 1) {
                    x += k;
                }
            }
        }

        System.out.println(x);

    }
}