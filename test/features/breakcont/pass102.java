class Test {
    /**
     * Nested loop control
     */

    public static void main(String[] args) {
        int x = 0;
        int y = 0;

        while (true) {
            y = 0;
            
            while (true) {
                if (y > 3) {
                    break;
                } else {
                    y += 1;
                }
            }

            System.out.println(x);      // >>> [0,9]
            System.out.println(y);      // >>> 4 (every time)

            if (x > y) break;

            x += 1;
        }

        System.out.println();           // \n
        System.out.println(x);          // 5 
    }
}