class test {

    public static String bloga;
    public static int i;

    public static void main(String[] args) {

        for (int i = 0, j = 60; i < j; i = i + 2, j = j + 1) {

            if (i > 20) continue;

            System.out.println(i);

            i += 2;
        }


    }
}
