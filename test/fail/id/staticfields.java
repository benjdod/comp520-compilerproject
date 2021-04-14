class Test {

    public static int x;
    public int y;

    public static void staticOnly() {

        int i = x + y;  // this should fail
    }
    
}
