class Test {

    /**
     * For loop control
     */

    public static void main(String[] args) {

        int x = 0;

        for (int i = 0;; i -= 1) {

            for (int j = i; j < 0; j += 1) {

                if (j > -4) continue;
                x = j;
            }

            if (i < -5) break;
        }

        System.out.println(-x);

    }
}