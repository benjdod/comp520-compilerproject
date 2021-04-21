class Test {
	public int x;
	public static A a;
	
	/*
	public static int weird(int m) {

		int z = 2;
		
		if (true) {
			int tempInt = 54;
			m = m + tempInt + z;
		}
				
		return m;
			
	}
	*/
	
	public static void go() {
		System.out.println(6642);
	}
	
	public void runCase() {
		x = 3;
		while (x < 10) {
			int t = incX();
		}
	}
	
	public int incX() {
		this.x = x + 1;
		return x;
	}
	
	public Test getThis() {
		return this;
	}
	
	public static void main(String[] args) {
		
		
		if (5 == 5 || 5/0 == 9) {
			System.out.println(11111);
		} 

		Test t1 = new Test();
		
		A a1 = new A();
		A a2 = new A();
		
		int x = 0;
		
		if (a == null) {
			x = 1;
		}
		
		System.out.println(x);
		
		//a.x = 4;
		
		//Test.a = a;
		//System.out.println(Test.a.x);
		
		t1.runCase();
			
		System.out.println(t1.x);
								
	}

}

class A {
	public int x;
}

class B {
	public int x;
}