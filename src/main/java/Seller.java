public class Seller {

    static final int WAIT_TIME = 1000;
    private final Dealership dealership;

    public Seller(Dealership dealership) {
        this.dealership = dealership;
    }

    public void receiveCar() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(WAIT_TIME);
                synchronized (this) {
                    dealership.getCars().add(new Car());
                    System.out.printf("%s выпустил 1 авто \n", Thread.currentThread().getName());
                    notify();
                }
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public synchronized Car sellCar() {
        try {
            System.out.printf("%s зашел в салон\n", Thread.currentThread().getName());
            while (dealership.getCars().size() == 0) {
                System.out.println("Машин нет");
                wait();
            }
            System.out.printf("%s купил машину\n", Thread.currentThread().getName());
            dealership.addSold();
            return dealership.getCars().remove(0);
        } catch (InterruptedException e) {
            return null;
        }
    }
}
