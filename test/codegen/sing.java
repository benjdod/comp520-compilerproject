class Test {
	public int x;
	public static int st;
	
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

		Test t1 = new Test();
		
		Test.st = 3;
		
		t1.runCase();
			
		System.out.println(t1.x);
								
	}

}