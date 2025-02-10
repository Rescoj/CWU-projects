import java.util.Random;
import java.util.Stack;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.Scanner;
public class TimeSumalationExample
{

    //declaring variables
    static Stack <Integer> stack = new Stack();
    static int customersServed = 0;
    static Heap heap = new Heap();
    static int randomInt;
    static Random random = new Random();
    static int counter = 0;
    static Timer timer = null;
    static Scanner scanner = new Scanner(System.in);
    static int response = scanner.nextInt();
    static Teller [] tellers = new Teller[response];
    static int tellerCompletionTimes [] = new int[response];

    public static void main(String[] args)
    {
        System.out.println("How many tellers are there?");
        scanner.close();
        initializeTellers();
        timer = new Timer(1, new TimerListener());
        timer.start();
        while(true){}
    }

    //method that initializes teller objects in a Teller array, also initializes teller completion times
    public static void initializeTellers()
    {
        System.out.println("Teller length = " + tellers.length);
        for (int i = 0; i < tellers.length; i++)
        {
            tellers[i] = new Teller();
            tellerCompletionTimes[i] = 0;
        }
    }

    private static class TimerListener implements ActionListener
    {

        //driver for the simulation
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("customers in line: " + heap.size());
            counter++;
            System.out.println("Tick "+ counter +"\n===============================================");
            event();
            serveCustomer();
            if (counter == 100)
            {
                timer.stop();
                System.out.println("Customers not served " + heap.size());
                System.out.println("Customers served " + customersServed);
                System.out.println("Average wait time: " + avgWaitTime() + " ticks");
                System.exit(0);
            }
        }
    }

    //method to compute average wait time
    public static int avgWaitTime()
    {
        int set = stack.size();
        int avgSum = 0;
        while (!stack.isEmpty())
        {
            avgSum = (avgSum + stack.pop());
        }
        return avgSum / set;
    }

    //customer service logic, if a customer is waiting a teller will be appointed to them with a designated completion time
    //also check to see if current tick is a completion tick to relieve tellers
    public static void serveCustomer ()
    {
        Customer max = (Customer) heap.max();
        for (int i = 0; i < tellers.length; i++)
        {
            if (!tellers[i].isBusy && !heap.isEmpty())
            {
                stack.push(counter - max.initWaitTime);
                tellers[i].isBusy = true;
                tellerCompletionTimes[i] = (max.serveTime + counter);
                System.out.println("Customer being served by teller " + (i + 1) + " will complete at " + tellerCompletionTimes[i]);
                heap.deleteMaximum();
            }
            if (tellerCompletionTimes[i] == counter)
            {
                customersServed++;
                System.out.println("Customer served, now accepting new customer");
                tellers[i].isBusy = false;
                tellerCompletionTimes[i] = 0;
            }
        }
    }

    //event handler
    public static void event ()
    {
        randomInt = random.nextInt(0,100);
        if (randomInt < 10)
        {
            System.out.println("New customer waiting");
            heap.insert(new Customer(random.nextInt(0,10),random.nextInt(5,20),counter));
        }
        else if (randomInt >= 10 && randomInt < 20)
        {
            //delete customer
        }
        else if (randomInt >= 20)
        {
            //do nothing (buffer)
        }
    }
}