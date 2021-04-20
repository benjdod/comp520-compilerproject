class Test {
	public int x;
	public int y;
	
	public static void sing(int n) {
		int j = n;
	}
	
	public void incX() {
		x = x + 1;
	}
	
	public void setX(int n) {
		x = n;
	}
	
	public void setY(int n) {
		y = n;
	}
	
	public int getSum() {
		return x+y;
	}
	
	public static void main(String[] args) {
		
		Test t = new Test();
		
		t.setX(5);
		t.setY(3);
		
		System.out.println(t.getSum());
						
	}

}