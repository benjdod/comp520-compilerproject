class Test {

    /**
     * Simple break and continue
     */
    public static void main(String[] args) {
        int x = 0;

        while (x < 10) {
            if (x >= 5) {break;}

            x += 1;
        }


        int y = 0;
        int z = 0;

        while (y < 150) {

            y += 1;

            if (z >= 100) {
                continue;
            } else {
                z = y * y;

            }
        }

        System.out.println(x);      //      >>> 5 
        System.out.println(z);      //      >>> 100

    }
}