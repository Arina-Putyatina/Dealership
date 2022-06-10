public class Main {

    public static void main(String[] args) throws InterruptedException {

        final Dealership dealership = new Dealership();
        final int waitTime = 1000;
        final int needSell = 10;

        while (dealership.getSold() < needSell) {
            ThreadGroup threadGroup = new ThreadGroup("main");
            Thread thread1 = new Thread(threadGroup, dealership::sellCar, "Покупатель1");
            Thread thread2 = new Thread(threadGroup, dealership::sellCar, "Покупатель2");
            Thread thread3 = new Thread(threadGroup, dealership::sellCar, "Покупатель3");
            Thread thread4 = new Thread(threadGroup, dealership::receiveCar, "Производитель");
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            Thread.sleep(waitTime);
            threadGroup.interrupt();
        }
    }
}
