class A {	
	
	public static int x;
	
	void poop() {
		this.sing();
	}
	
	void sing() {
		System.out.println(-1111);
	}
	
	public static void main(String[] args) {
				
		System.out.println(10101);
		
		B b = new B();
		C c = new C();
		b.c = c;
		
		A a = new A();
		
		//System.out.println(A.x);
		
		a.poop();
		
		b.c.x = 15;
		b.c.x = b.c.x + 15 - (c.x * 7);
		b = b;
		c = c;
		
		c.goo(c, b);
		System.out.println(c.goo(c, null));
		
		System.out.println(b.c.getX());
		System.out.println(90909);
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
	
	public int goo(C c, B b) {
		int out = 0;
		
		if (b != null) {
			out = 1;
		}
		return out + this.x + c.x;
	}
}

class B {
	public C c;
}