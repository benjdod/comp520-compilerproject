class Fail {

    /**
     * Conflicting method signatures.
     * Visibility doesn't matter.
     */
    

    public static void main(String[] args) {
        Fail f = new Fail();
        f.x();
        x();
    }

    public static int x() {
        return 1;
    } 
    public int x() {
        return 2;
    }
}
