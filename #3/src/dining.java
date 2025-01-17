package assignment3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class dining
{
    public static void main(String args[])
    {
        System.out.println("Starting the Dining Philosophers Simulation\n");
        miscsubs.InitializeChecking();
        
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 0 ; i<miscsubs.NUMBER_PHILOSOPHERS; i++) {
            pool.execute(new Philosopher(i));
        }
        
        pool.shutdown();
        
        miscsubs.LogResults();
        
    }
};



