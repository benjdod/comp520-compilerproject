class Test {
    A a;
    public void sing(int x, int y) {
        A.doNothing(x,y);
        a = new A();
        A[] arr = new A[10];
        A z = arr[5+4];

        if (arr != null) {
            System.out.println(x);
            String s = new String();
            if (s != null) {
                System.out.println(x);
            }
        }

        int g = 4;
    }    
}

class A {
    public static int doNothing(int x, int t) {
        while (true) {
            if (x > 3) {
                return x;
            }
        }
    }
}