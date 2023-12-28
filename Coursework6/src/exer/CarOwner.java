package exer;

import java.util.ArrayList;
import java.util.List;

public class CarOwner {
    private String name;

    private List<ParkingRecord> parkingRecords;

    private int totalAmount;

    private int activePoints;

    public CarOwner(String name) {
        this.name = name;
        this.parkingRecords = new ArrayList<>();
        this.activePoints = 0;
        this.totalAmount = 0;
    }

    public void addParkingRecord(ParkingRecord parkingRecord) {
        parkingRecords.add(parkingRecord);

        Car car = parkingRecord.getCar();
        double hours = parkingRecord.getParkingHours();
        int price = car.parkingPrice(hours);
        this.totalAmount += price;

        int activePointsIncrement = car.increasePoints(hours);
        this.activePoints += activePointsIncrement;
    }

    public String toString() {
        return String.format("[%s] Parting %d times, cost %d RMB, activePoints is %d",
                this.name, this.parkingRecords.size(), this.totalAmount, this.activePoints);
    }

    public List<ParkingRecord> getParkingRecords() {
        return parkingRecords;
    }
}
