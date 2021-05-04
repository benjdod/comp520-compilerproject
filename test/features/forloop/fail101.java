class Test {

    /**
     * duplicate declaration in for loop
     */

    public static void main(String[] args) {

        for (int i = 0; i < 10; i += 1) {

            int i = 1;

            System.out.println(i);
            
        }

    }
}