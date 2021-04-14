class Test {
    A a;
    public void sing(int x, int y) {
        y = A.doNothing(x);

        int g = 4;
    }    
}

class A {
    public static int doNothing(int x, int t) {
        while (true) {
            if (x > 3) {
                return x;
            }

            if (t > 0) {
                x = x + t;
            } else {
                x = x - (t - 4);
            }
        }

        return 0;
    }
}