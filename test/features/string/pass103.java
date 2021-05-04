class Test {

    /**
     * Printing a null String reference
     */

    public static String s;

    public static void main(String[] args) {

        s = null;

        System.out.println(s);

        s = "not null anymore";

        System.out.println(s);

    }
}