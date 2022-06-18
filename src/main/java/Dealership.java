import java.util.ArrayList;
import java.util.List;

public class Dealership {

    private Seller seller = new Seller(this);
    private List<Car> cars = new ArrayList<>(10);
    private int sold;
    private final int needSell = 10;

    public int getNeedSell() {
        return needSell;
    }

    public int getSold() {
        return sold;
    }

    public void addSold() {
        sold++;
    }

    public Car sellCar() {
        return seller.sellCar();
    }

    public void receiveCar() {
        seller.receiveCar();
    }

    List<Car> getCars() {
        return cars;
    }

}
