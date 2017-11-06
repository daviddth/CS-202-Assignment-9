import static org.junit.Assert.*;

public class kth_AVLTest {
    public void main () throws Exception {
        kth_AVL tester = new kth_AVL();
        int testUserInputKey1 = 5;
        Integer[] testSomeList1 = new Integer[10];
        testSomeList1[0] = 1;
        testSomeList1[1] = 2;
        testSomeList1[2] = 3;
        testSomeList1[3] = 3;
        testSomeList1[4] = 5;
        testSomeList1[5] = 7;
        testSomeList1[6] = 8;
        assertEquals("return 1 2 3 3 5 7 8", 1233578, tester.main(testSomeList.insert(i)));

    }

}