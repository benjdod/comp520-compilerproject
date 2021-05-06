class Test {

    /**
     * Nested ternary expressions.
     * Valid but smelly code.
     */


    public static void main(String[] args) {

        int n = 6;
        
        int x = (n < 8)         // true
            ? (n > 4)           // true
                ? (n <= 6)      // true 
                    ?   n-3     // x 3
                    :   10
                : 0
            : (n < -5) 
                ? 6
                : 2700;


        System.out.println(x);  

    }
}
