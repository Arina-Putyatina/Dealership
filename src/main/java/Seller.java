import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {

    static final int WAIT_TIME = 1000;

    private final Dealership dealership;
    private final Lock lock;
    private final Condition condition;

    public Seller(Dealership dealership) {

        this.dealership = dealership;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void receiveCar() {
        int needSell = dealership.getNeedSell();
        while (dealership.getSold() < needSell) {
            try {
                lock.lock();
                System.out.printf("%s выпустил 1 авто \n", Thread.currentThread().getName());
                dealership.getCars().add(new Car());
                condition.signalAll();
            } catch (IllegalMonitorStateException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Car sellCar() {

        try {
            if (Thread.currentThread().isInterrupted()) {
                return null;
            }
            int needSell = dealership.getNeedSell();
            lock.lock();
            System.out.printf("%s зашел в салон\n", Thread.currentThread().getName());
            while (dealership.getCars().size() == 0 && !Thread.currentThread().isInterrupted() && dealership.getSold() < needSell) {
                System.out.println("Машин нет");
                condition.await();
            }
            if (dealership.getSold() < needSell) {
                System.out.printf("%s купил машину\n", Thread.currentThread().getName());
                dealership.addSold();
                return dealership.getCars().remove(0);
            } else {
                System.out.println("Продажа остановлена");
                return null;
            }

        } catch (InterruptedException | IllegalMonitorStateException ex) {
            return null;
        } finally {
            lock.unlock();
        }
    }
}
