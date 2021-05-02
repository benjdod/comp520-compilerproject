class Test {

    public static int x;

    public static void main(String[] args) {

        int z = 1;


        while (z < 10) {

            if (z > 7) {
                z = 9;
                continue;
            }

            z += 1;
        }


        System.out.println(z);
    }
}
