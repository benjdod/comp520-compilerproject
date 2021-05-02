class Test {

    /**
     * Nested ternary expressions
     */


    public static void main(String[] args) {

        int n = 6;
        
        int x = (n < 8)         // true
            ? (n > 4)           // true
                ? (n <= 6)      // true 
                    ?   n+1     // x = 7
                    :   10
                : 0
            : (n < -5) 
                ? 6
                : 2700;


        System.out.println(x);  // >>> 7
    }
}
