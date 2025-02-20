import java.io.*;
import java.util.*;
public class Lab5
{
    /**
     * Problem: Sort arr into blocks of the same length using counting sort.
     *
     */
    private static void problem(byte[][] arr)
    {

        //declaring variables
        int [] count = new int[128];
        byte [][] sortedArr = new byte[arr.length][];

        //populating the count array
        for (int i = 0; i < arr.length; i++)
        {
            count[arr[i].length]++;
        }

        //performing cumulative summation on each element of the count array
        for (int i = 1; i < count.length; i++)
        {
            count[i] = count[i - 1] + count[i];
        }

        //performing the counting sort
        for (int i = 0; i < arr.length; i++)
        {
            sortedArr[count[arr[i].length] - 1] = arr[i].clone();
            count[arr[i].length]--;
        }

        //repopulating output array with sorted array
        for (int i = 0; i < sortedArr.length; i++)
        {
            arr[i] = sortedArr[i];
        }
    }
    // ---------------------------------------------------------------------
// Do not change any of the code below!
    private static final int LabNo = 5;
    private static final String quarter = "Spring 2024";
    private static final Random rng = new Random(654321);
    private static boolean testProblem(byte[][] testCase)
    {
        byte[][] numbersCopy = new byte[testCase.length][];
// Create copy.
        for (int i = 0; i < testCase.length; i++)
        {
            numbersCopy[i] = testCase[i].clone();
        }
// Sort
        problem(testCase);
        Arrays.sort(numbersCopy, new numberComparator());
        if(testCase.length != numbersCopy.length)
        {
            return false;
        }
        for (int i = 0; i < testCase.length; i++)
        {
            if(testCase[i].length != numbersCopy[i].length)
            {
                return false;
            }
        }
        return true;
    }
    private static class numberComparator implements Comparator<byte[]>
    {
        @Override
        public int compare(byte[] n1, byte[] n2)
        {
            return n1.length - n2.length;
        }
    }
    public static void main(String args[])
    {
        System.out.println("CS 302 -- " + quarter + " -- Lab " + LabNo);
        testProblems();
    }
    private static void testProblems()
    {
        int noOfLines = 10000;
        System.out.println("-- -- -- -- --");
        System.out.println(noOfLines + " test cases.");
        boolean passedAll = true;
        for (int i = 1; i <= noOfLines; i++)
        {
            byte[][] testCase = createTestCase(i);
            boolean passed = false;
            boolean exce = false;
            try
            {
                passed = testProblem(testCase);
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                passed = false;
                exce = true;
            }
            if (!passed)
            {
                System.out.println("Test " + i + " failed!" + (exce ? "(Exception)" : ""));
                passedAll = false;
                break;
            }
        }
        if (passedAll)
        {
            System.out.println("All test passed.");
        }
    }
    private static byte[][] createTestCase(int testNo)
    {
        int maxSize = Math.min(100, testNo) + 5;
        int size = rng.nextInt(maxSize) + 5;
        byte[][] numbers = new byte[size][];
        for (int i = 0; i < size; i++)
        {
            int digits = rng.nextInt(maxSize) + 1;
            numbers[i] = new byte[digits];
            for (int j = 0; j < digits - 1; j++)
            {
                numbers[i][j] = (byte)rng.nextInt(128);
            }
// Ensures that the most significant digit is not 0.
            numbers[i][digits - 1] = (byte)(rng.nextInt(127) + 1);
        }
        return numbers;
    }
}
