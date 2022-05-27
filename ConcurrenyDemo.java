import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrenyDemo {

    public static void main(String args[]){

        try {
            Semaphore sem = new Semaphore(10);
            CyclicBarrier cb = new CyclicBarrier(10);
            System.out.println("Ready ...");
            Thread.sleep(5000);
            System.out.println("Get set ...");
            Thread.sleep(4000);
            System.out.println("G0 ...");
            Thread.sleep(1000);
            new Bike("Ramesh", cb, sem);
            new Bike("Rajesh", cb, sem);
            new Bike("Robin",cb,sem);
            new Bike("Jack",cb,sem);
            new Bike("Brigesh",cb,sem);
            new Bike("Arjun",cb,sem);
            new Bike("Aravind",cb,sem);
            new Bike("Ram",cb,sem);
            new Bike("Gokul",cb,sem);
            new Bike("Satheesh",cb,sem);

        }catch(Exception e){
            System.out.println(e);
        }


    }
}

class Result{

    static AtomicInteger position = new AtomicInteger(0);
    static ConcurrentHashMap <Integer,String> bikeMap = new ConcurrentHashMap<>();

}

class Bike extends Thread{

    String bname;
    CyclicBarrier cb;

    Semaphore sem;
    int distance;
    int speed;

    int time;

    boolean raceOver = false;
    Random speedGen = new Random();

    int finalDest = 100;

    Bike(String name,CyclicBarrier cbs ,Semaphore s){
        bname = name;
        cb = cbs;
        sem = s;
        distance = 0;
        speed = speedGen.nextInt(20)+4;
        new Thread(this).start();
    }

    public void run(){
        try {
            System.out.println(bname + " starts with speed " + speed + " at position " + distance);
            Thread.sleep(5000);
            cb.await();
            synchronized (this) {

            while (!raceOver) {
                sem.acquire();
                if (distance >= finalDest) {
                    raceOver = true;
                    sem.release();
                    break;
                }
                Thread.sleep(2000);
                System.out.println("Bike " + bname + " at position " + distance + " with speed " + speed);
                distance += speed;
                sem.release();
            }

            if (raceOver) {
                float cTime = (float) (finalDest / speed);
                Result.bikeMap.put(Result.position.incrementAndGet(), bname);
                System.out.println("Bike " + bname + " reached finish line in " + cTime + " seconds");
            }
            System.out.println("-----------------------------");
            System.out.println("Race results ");
            System.out.print(Result.bikeMap);
            System.out.println("-----------------------------");
        }
        }catch (Exception e ){
            System.out.println(e);
        }

    }

}
