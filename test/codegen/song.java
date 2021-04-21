class A {	
	
	static void poop() {
		
	}
	
	public static void main(String[] args) {
				
		System.out.println(2);
		
		B b = new B();
		C c = new C();
		b.c = c;
		
		int t = b.c.getX();

		System.out.println(b.c.getX());
		
		
		b.c.x = 15;
		b.c.x = b.c.x + 15 - (c.x * 7);
		b = b;
		c = c;
		
		System.out.println(b.c.getX());
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