class Test {

    public static int x;

    public static int x() {
        return 1;
    }

    public static int x(int y) {
        x = 1 + y;
        return 1 + y;
    }

    public static int x(int z, int q) {
        return z + q;
    }

    public static void main(String[] args) {

        x(4);
        x();

        int z  = 4;

        z *= 1 + 2;


        System.out.println(z);
    }
}
