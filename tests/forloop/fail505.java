class Test {

    /**
     * duplicate declaration in nested for loop
     */

    public static void main(String[] args) {

        for (int i = 0; i < 10; i += 1) {

            for (int i = 0; i < 10; i += 1) {
                System.out.println(i);
            }
        }

    }
}