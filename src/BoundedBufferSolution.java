import java.util.Random;

public class BoundedBufferSolution {

    public static final int N = 200;
    static int[] buffer = new int[N];

    public static void main(String[] args){
        Random rand = new Random();

        //1.) "The buffer is a large array of n integers, initialized to all zeros."
        for(int i = 0; i < buffer.length; i++){
            buffer[i] = 0;
        }
        System.out.println("Beginning buffer:  ");
        printBuffer();

        //2.) "The producer and the consumer are separate concurrent threads in a process."
      /*  Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                int nextIn = 0;
                //1.) "The producer executes short bursts of random duration.
                while(true){
                    //Get random #, k1:
                    int k1 = rand.nextInt(buffer.length/4);     //ensures k1 is some less than the buffer size!
                    for(int i = 0; i <= k1; i++){
                        //"producer adds a 1 to the next k1 slots of the buffer, modulo n"
                        buffer[(nextIn + i) % N] = 1;
                    }
                    nextIn  = (nextIn + k1) % N;

                    //Get random number t1:
                    int t1 = ((int)((Math.random() * (7 - 3)) + 3))*1000;       //choose random number b/w 3-7 + convert to seconds

                    try {
                        //Sleep for t1 seconds
                        Thread.sleep(t1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });


        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                int nextOut = 0;
                while(true){
                    //Get random number t2:
                    int t2 = ((int)((Math.random() * (7 - 3)) + 3))*1000;       //choose random number b/w 3-7 + convert to seconds
                    try {
                        //sleep for t2 seconds:
                        Thread.sleep(t2);

                        //Get random number k2:
                        int k2 = rand.nextInt(buffer.length/4);     //ensures k2 is some less than the buffer size!

                        for(int i = 0; i <= k2; i++){

                            //"If any slot contains a number greater than 1, then a race condition has been detected: The consumer was unable to keep up and thus the producer has added a 1 to a slot that has not yet been reset."
                            int data = buffer[(nextOut + i) % N];
                            if(data != 1){
                                System.out.println("Race condition encountered at space: " + (nextOut + i % N) + "! Exiting...");
                                System.exit(0);
                            }

                            //the consumer reads the next k2 slots and resets each to 0.
                            buffer[(nextOut + i) % N] = 0;
                        }
                        nextOut = (nextOut + k2) % N;
                        printBuffer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });*/

        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
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
