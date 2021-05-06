class Test {
    /**
     * Nested loop control
     */

    public static void main(String[] args) {
        int x = 0;
        int y = 10;

        while (y > 0) {
            x = 0;
            
            for (;;) {
                if (x >= 4) {
                    break;
                } else {
                    x += 1;
                }
            }

            if (y == 0) continue;

            x -= y;
            y -= 1;

        }

        System.out.println(x);     
    }
}