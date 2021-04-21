class A {	
	
	public static int x;
	
	void poop() {
		this.sing();
	}
	
	void sing() {
		System.out.println(-1111);
	}
	
	public static void main(String[] args) {
				
		System.out.println(11111);
		
		B b = new B();
		C c = new C();
		b.c = c;
		
		A a = new A();
		
		System.out.println(A.x);
		
		a.poop();
		
		b.c.x = 15;
		b.c.x = b.c.x + 15 - (c.x * 7);
		b = b;
		c = c;
		
		System.out.println(b.c.getX());
		System.out.println(99999);
	}//
}

class C {
	public int x;
	/*
	public void sing() {
		System.out.println(1234);
	}*/
	
	public int getX() {
		return x;
	}
}

class B {
	public C c;
}