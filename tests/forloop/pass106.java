class Test {

    /**
     * complex array assignment
     */

    public static void main(String[] args) {

        A.makeNewArray(10);

        int k = 0; 

        // FIXME: no array type!
        for (int[] arr = new int[10]; k < arr.length ; k+= 1) {
            arr[k] = k % 3;

            // note that a direct array assignment statement
            // (e.g. A.getArray()[9-k] = 14) would not be 
            // a valid miniJava statement since array assignment
            // must be by reference, not expression.
            int[] aArray = A.getArray();       
            aArray[9-k] = 14; 
        }
    }
}

class A {
    private static int[] arr;

    public static void makeNewArray(int len) {
        arr = new int[len];
    }

    public static int[] getArray() {
        return arr;
    }
}
