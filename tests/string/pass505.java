class Test {

    /**
     * setter methods.
     */


    private String message;
    private int num;

    public static void main(String[] args) {
        Test t = new Test();

        t.setMessage("five");
        t.setNumber(10);
        t.print();
    }

    public void setMessage(String s) {
        this.message = s;
    }

    public void setNumber(int n) {
        this.num = n;
    }

    public String getMessage() {
        return this.message;
    }

    public void print() {
        int i = 0;
        while (i < num) {
            System.out.println(this.message);
            i += 1;
        }
    }
}