import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {

    private final Dealership dealership;
    private final Lock lock;
    private final Condition condition;

    public Seller(Dealership dealership) {

        this.dealership = dealership;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void receiveCar() {
        try {
            lock.lock();
            System.out.printf("%s выпустил 1 авто \n", Thread.currentThread().getName());
            int timeAcceptance = 200;
            Thread.sleep(timeAcceptance);
            dealership.getCars().add(new Car());
            condition.signal();
        } catch (InterruptedException | IllegalMonitorStateException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public Car sellCar() {

        try {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            try {
                lock.lock();
                System.out.printf("%s зашел в салон\n", Thread.currentThread().getName());
                while (dealership.getCars().size() == 0 && !Thread.currentThread().isInterrupted()) {
                    System.out.println("Машин нет");
                    int waitTime = 400;
                    condition.wait(waitTime);
                }
                int timeSale = 300;
                Thread.sleep(timeSale);
                System.out.printf("%s купил машину\n", Thread.currentThread().getName());
                dealership.addSold();
                return dealership.getCars().remove(0);

            } catch (InterruptedException | IllegalMonitorStateException ex) {
                return null;
            } finally {
                lock.unlock();
            }

        } catch (InterruptedException e) {
            return null;
        }
    }
}
