public class Seller {

    static final int WAIT_TIME = 1000;
    private final Dealership dealership;

    public Seller(Dealership dealership) {
        this.dealership = dealership;
    }

    public void receiveCar() {
        int needSell = dealership.getNeedSell();
        while (dealership.getSold() < needSell) {
            try {
                synchronized (this) {
                    dealership.getCars().add(new Car());
                    System.out.printf("%s выпустил 1 авто \n", Thread.currentThread().getName());
                    notify();
                    Thread.sleep(WAIT_TIME);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized Car sellCar() {

        try {
            if (Thread.currentThread().isInterrupted()) {
                return null;
            }
            int needSell = dealership.getNeedSell();
            System.out.printf("%s зашел в салон\n", Thread.currentThread().getName());
            while (dealership.getCars().size() == 0 && !Thread.currentThread().isInterrupted() && dealership.getSold() < needSell) {
                System.out.println("Машин нет");
                wait();
            }
            if (dealership.getSold() < needSell) {
                System.out.printf("%s купил машину\n", Thread.currentThread().getName());
                dealership.addSold();
                return dealership.getCars().remove(0);
            } else {
                System.out.println("Продажа остановлена");
                return null;
            }

        } catch (InterruptedException e) {
            return null;
        }
    }
}
