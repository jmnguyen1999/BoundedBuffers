import java.util.Random;

public class Consumer implements Runnable{

    int[] buffer;
    int N;

    public Consumer(/*int[] buffer*/) {
        this.buffer = BoundedBufferSolution.buffer;

        N = buffer.length;
    }

    @Override
    public void run() {
        int nextOut = 0;
        Random rand = new Random();

        while(true){
            //Get random number t2:
            int t2 = ((int)((Math.random() * (7 - 3)) + 3))*1000;       //choose random number b/w 3-7 + convert to seconds
            try {
                //sleep for t2 seconds:
                System.out.println("Consumer sleeping for " + t2/1000 + " s");
                Thread.sleep(t2);

                //Get random number k2:
                int k2 = rand.nextInt(buffer.length/4);     //ensures k2 is some less than the buffer size!
                System.out.println("Consumer turn writing 0's & checking buffer before doing so..");
                System.out.println("Buffer before Consumer writing: ");
                printBuffer();
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
                System.out.println("Buffer after Consumer done writing: ");
                printBuffer();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void printBuffer(){
        for(int i = 0; i < buffer.length; i++){
            System.out.print(buffer[i]);
        }
        System.out.println("\n");
    }
}
