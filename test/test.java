class Test {

    public static void main(String[] args) {

        C c = new C();
        c.x = 18;
        B b = new B();
        b.c = c;

        A.b = b;

        System.out.println(B.c.x);

    }
}

class A {
    public static B b;
}

class B {
    public C c;
}

class C {
    public int x;
}