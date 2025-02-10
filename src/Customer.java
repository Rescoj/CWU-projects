public class Customer implements Comparable
{

    //declaring class variables
    int initWaitTime;
    int priority;
    int serveTime;

    //constructor
    public Customer(int priority, int serveTime, int initWaitTime)
    {
        this.initWaitTime = initWaitTime;
        this.priority = priority;
        this.serveTime = serveTime;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
