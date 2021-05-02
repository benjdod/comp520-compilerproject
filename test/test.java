class Test {

    public static int x;

    public static int x() {
        return 1;
    }

    public static int x(int y) {
        x = 1 + y;
        return 1 + y;
    }

    public static void main(String[] args) {

        x(4);
        x();

        //System.out.println(Test.x);

    }
}
