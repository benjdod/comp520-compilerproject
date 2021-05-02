class Test {

    /**
     * Overloading System.out.println to support
     * none and boolean args.
     * Boolean support is more hacky than anything, but
     * it can be implemented easily.
     */
    public static void main(String[] args) {
        System.out.println();       //      \n
        System.out.println();       //      \n
        System.out.println();       //      \n
        System.out.println(false);  //      false
        System.out.println(4);      //      >>> 4
        System.out.println(true);   //      true
        System.out.println();       //      \n
        System.out.println();       //      \n  
        System.out.println();       //      \n
        System.out.println();       //      \n

    }
}
