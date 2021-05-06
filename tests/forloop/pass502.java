class Test {

    /**
     * multiple declarations, increments
     */

    public static void main(String[] args) {

        int x = 0;

        for (int i = 0, j = 20; (i <= j); i += 1, j -= 1) {

            // these will converge and just add 20 to x

            x += i + j;
        }

        x = x - (20 * 11)  + 2;

        System.out.println(x);

    }
}