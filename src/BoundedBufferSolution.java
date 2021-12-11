import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class BoundedBufferSolution {

    public static final int N = 100;
    static int[] buffer = new int[N];

    //Semaphore used to fix race condition issue:
    public static Semaphore emptySpace = new Semaphore(1);           //only 1 permit
    public static Semaphore filledSpace = new Semaphore(0);

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter one: (1) Run to show race condition (2) Run to show race condition FIXED using semaphores!");
        int answer = sc.nextInt();

        Random rand = new Random();

        //1.) "The buffer is a large array of n integers, initialized to all zeros."
        for(int i = 0; i < buffer.length; i++){
            buffer[i] = 0;
        }
        System.out.println("Beginning buffer:  ");
        printBuffer();

        //2.) "The producer and the consumer are separate concurrent threads in a process."
        Thread producer = new Thread(new Producer(answer));
        Thread consumer = new Thread(new Consumer(answer));
        producer.start();
        consumer.start();
    }

    //Purpose:
    public static void printBuffer(){
        for(int i = 0; i < buffer.length; i++){
            System.out.print(buffer[i]);
        }
        System.out.println("\n");
    }
}
