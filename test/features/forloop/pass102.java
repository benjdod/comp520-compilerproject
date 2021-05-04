class Test {

    /**
     * multiple declarations, increments
     */

    public static void main(String[] args) {

        for (int i = 0, j = 20; (i <= j); i += 1, j -= 1) {
            System.out.println(i);      // 0, 1, 2, ... 10
            System.out.println(j);      // 20, 19, 18, ... 10
            System.out.println();       // \n
        }

    }
}