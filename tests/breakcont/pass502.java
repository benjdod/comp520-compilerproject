
class Test {
    /**
     * simple continue
     */

    public static void main(String[] args) {
        int x = 0;

        for (int i = 0; i < 20;i+=1) {
            if (x >= 2) continue;
            else x += 1;
        }

        System.out.println(x);
        
    }
}