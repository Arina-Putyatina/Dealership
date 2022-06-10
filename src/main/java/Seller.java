public class Seller {

    private final Dealership dealership;

    public Seller(Dealership dealership) {
        this.dealership = dealership;
    }

    public synchronized void receiveCar() {
        try {
            System.out.printf("%s выпустил 1 авто \n", Thread.currentThread().getName());
            int timeAcceptance = 200;
            Thread.sleep(timeAcceptance);
            dealership.getCars().add(new Car());
            notifyAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    public synchronized Car sellCar() {

        try {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            try {
                System.out.printf("%s зашел в салон\n", Thread.currentThread().getName());
                while (dealership.getCars().size() == 0 && !Thread.currentThread().isInterrupted()) {
                    System.out.println("Машин нет");
                    int waitTime = 400;
                    wait(waitTime);
                }
                int timeSale = 300;
                Thread.sleep(timeSale);
                System.out.printf("%s купил машину\n", Thread.currentThread().getName());
                dealership.addSold();
                return dealership.getCars().remove(0);

            } catch (InterruptedException ex) {
                return null;
            }
        } catch (InterruptedException e) {
            return null;
        }
    }
}
