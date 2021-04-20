class A {
	public B b;
	
	public static void main(String[] args) {
		
		C c = new C();
		c.x = 31;
		
		
		B b = new B();
		b.c = c;
		
		A a = new A();
		a.b = b;
	}
}

class B {
	public C c;
}

class C {
	public int x;
}