class Fail {

    /**
     * Conflicting method signatures.
     */
    public int x() {
        return 1;
    } 
    public int x() {
        return 2;
    }

    public static void main(String[] args) {
        x();
    }
}
