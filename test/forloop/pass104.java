class Test {

    /**
     * For loop control
     */

    public static void main(String[] args) {

        int geometric = 0;

        for (int i = 0;; i -= 1) {

            for (int j = i; j < 0; j += 1) {

                if (j > -4) continue;
                System.out.println(j);      // this will never print anything greater than -4
            }

            if (i < -5) break;
        }

    }
}