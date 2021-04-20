class Test {
	public int x;
	boolean b;
	
	public static void sing(int n) {
		int j = n;
	}
	
	public void incX() {
		x = x + 1;
	}
	
	public static void main(String[] args) {
		
		int arr_len = 10;
						
		int[] arr1 = new int[arr_len];
				
		arr1[0] = 60;
		arr1[5] = 6;
		
		int i = 0;
		while (i < arr_len) {
			System.out.println(arr1[i]);
			i = i + 1;
		}
						
	}

}