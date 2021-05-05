class Test {


    /**
     * String equality by reference
     */

    public static void isEqual(String a, String b) {
        System.out.println(a == b ? "strings are equal" : "strings are not equal");
    }

    public static void main(String[] args) {

        String s5 = "...";
        String s2 = null;
        
        isEqual(s5, s2);

        s2 = s5;

        isEqual(s5, s2);
    }
}