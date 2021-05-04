class Test {


    /**
     * String equality by reference
     */

    public static void isEqual(String a, String b) {
        System.out.println(a == b ? "strings are equal" : "strings are not equal");
    }

    public static void main(String[] args) {

        String s1 = "...";
        String s2 = null;
        
        isEqual(s1, s2);

        s2 = s1;

        isEqual(s1, s2);
    }
}