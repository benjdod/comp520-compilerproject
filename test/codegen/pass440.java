class Example {
	
	public static void main(String[] args) {
		BubbleSort.start();
		BubbleSort.print();
		System.out.println(6666666);
		BubbleSort.sort();
		BubbleSort.print();    
	}
}

class BubbleSort{
	private static int[] data;
	
	public static void start() {
		data = new int[15];
		data[0] = -66;
		data[1] = 10;
		data[2] = 20;
		data[3] = 30;
		data[4] = 12;
		data[5] = 17;
		data[6] = -12;
		data[8] = 0;
		data[9] = 128;
		data[10] = 4;
		data[11] = 8;
		data[12] =-36;
		data[13] = -67;
		data[14] = 10000;
	}
	
	public static void sort() {
		int i = 0;
		while (i < data.length-1) {
			int j = 0;
			while(j < data.length-1) {
				int k = j + 1;
				if (data[j] >= data[k]) {
					int temp = data[k];
					data[k] = data[j];
					data[j] = temp;
				}
				j = j + 1;
			}
			i = i + 1;
		}
	}
	
	public static void print() {
		int i = 0;
		while (i < data.length) {
			System.out.println(data[i]);
			i = i + 1;
		}
	}
}









