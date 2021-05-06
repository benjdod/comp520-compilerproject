class Test {

    /**
     * Complex string refernce and assignment
     */

    public static void main(String[] args) {

        A a = new A();
        B b = new B();

        b.s = "two";
        a.b = b;

        System.out.println(a.b.s);

        X.setSecret("two");
        System.out.println(X.getSecret());
    }
}

class A {
    public B b;
}

class B {
    public String s;
}

class X {
    private static String secret;

    public static void setSecret(String message) {
        secret = message;
    }

    public static String getSecret() {
        return secret;
    }
}