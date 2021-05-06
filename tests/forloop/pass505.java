class Test {

    /**
     * empty statements
     */

    public static void main(String[] args) {

        boolean b = false;

        int x = 0;

        for (;x != 5;) {

            if (b) {
                
                break;
            } else {
                x += 1;
            }
        }

        System.out.println(x);

    }
}