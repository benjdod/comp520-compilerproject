class Test {

    /**
     * empty statements
     */

    public static void main(String[] args) {

        boolean b = false;

        for (;;) {

            if (b) {
                System.out.println("exiting");
                break;
            } else {
                System.out.println("setting b");
                b = true;
            }
        }

    }
}