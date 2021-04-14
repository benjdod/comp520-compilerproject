class Test {
    A a;

    void uhh() {
        int an_integer = a.b.c.d.x;
    }
}

class A {
    public B b;
}

class B {
    public C c;
}

class C {
    public D d;
}

class D {
    public static int x;
}
