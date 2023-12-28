package exer;

public class Car {
    Calculate calculate;

    String carNumber;

    public Car(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public int parkingPrice(double hours) {
        return this.calculate.calculateParkingPrice(hours);
    }

    public void setCalculate(Calculate calculate) {
        this.calculate = calculate;
    }

    public int increasePoints(double hours) {
        return this.calculate.calculatePoints(hours);
    }
}