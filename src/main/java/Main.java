public class Main {

    public static void main(String[] args) throws InterruptedException {

        final Dealership dealership = new Dealership();

        Thread thread1 = new Thread(null, dealership::receiveCar, "Производитель");
        thread1.start();

        int buyerNumber = 1;
        while (dealership.getSold() < dealership.getNeedSell()) {
            Thread thread2 = new Thread(null, dealership::sellCar, "Покупатель" + buyerNumber);
            buyerNumber++;
            Thread thread3 = new Thread(null, dealership::sellCar, "Покупатель" + buyerNumber);
            buyerNumber++;
            Thread thread4 = new Thread(null, dealership::sellCar, "Покупатель" + buyerNumber);
            buyerNumber++;

            thread2.start();
            thread3.start();
            thread4.start();

            thread2.join();
            thread3.join();
            thread4.join();
        }
    }
}
