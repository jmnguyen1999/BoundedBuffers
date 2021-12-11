import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable{

    int[] buffer;
    int N;
    Semaphore emptySpace;
    Semaphore filledSpace;
    boolean useSemaphores;

    public Producer(int answer) {
        this.buffer = BoundedBufferSolution.buffer;
        this.emptySpace = BoundedBufferSolution.emptySpace;
        this.filledSpace = BoundedBufferSolution.filledSpace;
        this.N = BoundedBufferSolution.N;
        if(answer == 1) {
            this.useSemaphores = false;
        }
        else{
            this.useSemaphores = true;
        }
    }

    @Override
    public void run() {

        int nextIn = 0;
        Random rand = new Random();

        if(!useSemaphores) {
            //1.) "The producer executes short bursts of random duration.
            while (true) {
                //Get random #, k1:
                int k1 = rand.nextInt(buffer.length / 4);     //ensures k1 is some less than the buffer size!
                System.out.println("Producer turn writing 1's");
                for (int i = 0; i <= k1; i++) {
                    //"producer adds a 1 to the next k1 slots of the buffer, modulo n"
                    buffer[(nextIn + i) % N] = 1;
                }
                nextIn = (nextIn + k1) % N;

                //Get random number t1:
                int t1 = ((int) ((Math.random() * (7 - 3)) + 3)) * 1000;       //choose random number b/w 3-7 + convert to seconds

                try {
                    //Sleep for t1 seconds
                    System.out.println("Producer sleeping for " + t1 / 1000 + " s");
                    Thread.sleep(t1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        else{
            int timesToIterate = 7;
            int count = 0;
            while(count <= timesToIterate){
                //Get random #, k1:
                int k1 = rand.nextInt(buffer.length/4);     //ensures k1 is some less than the buffer size!

                try{
                    emptySpace.acquire();       //Try to get the permit

                    System.out.println("Producer turn writing 1's");

                    for (int i = 0; i <= k1; i++) {

                        //If the buffer still has 1 in it --> consumer did NOT make it 0 in time --> consumer is too slow!
                        //--> open a permit for the consumer
                        if(buffer[(nextIn + i) % N] == 1) {
                            filledSpace.release();
                        }
                        //"producer adds a 1 to the next k1 slots of the buffer, modulo n"
                        buffer[(nextIn + i) % N] = 1;
                    }
                    nextIn = (nextIn + k1) % N;
                    //Get random number t1:
                    int t1 = ((int)((Math.random() * (7 - 3)) + 3))*1000;       //choose random number b/w 3-7 + convert to seconds

                    try {
                        //Sleep for t1 seconds
                        System.out.println("Producer sleeping for " + t1/1000 + " s");
                        Thread.sleep(t1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                    //producer is done one time --> let consumer get a permit and use the buffer
                    filledSpace.release();

                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
