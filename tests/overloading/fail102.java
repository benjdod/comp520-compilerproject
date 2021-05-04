class Fail {

    /**
     * Conflicting method signatures. 
     * Return type doesn't matter.
     */

    public void x(int y) {
        return;
    }

    public int x(int g) {
        return g + 1;
    }

    public static void main(String[] args) {
        x(1);
    }
}
