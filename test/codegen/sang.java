class A {
	public B b;
	
	public static void main(String[] args) {
		
		C c = new C();
		c.x = 31;
		
		
		B b = new B();
		b.c = c;
		
		A a = new A();
		a.b = b;
		
		a.b.c.getX();
		
		//System.out.println(a.b.c.x);
		
	}
}

class B {
	public C c;
	
}

class C {
	public int x;
	
	public int getX() {
		return x;
	}
}