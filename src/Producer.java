import java.util.Random;

public class Producer implements Runnable{

    int[] buffer;
    int N;

    public Producer(/*int[] buffer*/) {
       // this.buffer = buffer;
        this.buffer = BoundedBufferSolution.buffer;

        N = buffer.length;
    }

    @Override
    public void run() {

        int nextIn = 0;
        Random rand = new Random();

        //1.) "The producer executes short bursts of random duration.
        while(true){
            //Get random #, k1:
            int k1 = rand.nextInt(buffer.length/4);     //ensures k1 is some less than the buffer size!
            System.out.println("Producer turn writing 1's");
            for(int i = 0; i <= k1; i++){
                //"producer adds a 1 to the next k1 slots of the buffer, modulo n"
                buffer[(nextIn + i) % N] = 1;
            }
            nextIn  = (nextIn + k1) % N;

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
        }
    }
}
