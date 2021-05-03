class Test {

    public static int x;

    public static String global_string;

    public static void main(String[] args) {

        A.globalStr = 5;

        String s = A.globalStr;


    }
}

class A {
    public static String globalStr;
}