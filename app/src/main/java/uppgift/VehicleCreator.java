package uppgift;

import java.util.Random;

public class VehicleCreator {
    private Vehicle[] vehicles;

    public VehicleCreator(int numberOfVehicles) {
        vehicles = new Vehicle[numberOfVehicles];
        generateVehicles(numberOfVehicles);
    }

    private void generateVehicles(int numberOfVehicles) {
        for (int i = 0; i < numberOfVehicles; i++) {
            String regNumber;
            do {
                regNumber = generateRegNumber();
            } while (isDuplicate(regNumber));
            vehicles[i] = new Vehicle(regNumber);
        }
    }

    private String generateRegNumber() {
        Random rand = new Random();
        StringBuilder regNumber = new StringBuilder();
        for (int i = 0; i < 3; i++) { 
            char letter = (char) (rand.nextInt(26) + 'A');
            regNumber.append(letter);
        }
        for (int i = 0; i < 3; i++) { 
            int number = rand.nextInt(10);
            regNumber.append(number);
        }
        return regNumber.toString();
    }

    private boolean isDuplicate(String regNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle != null && vehicle.getRegistrationNumber().equals(regNumber)) {
                return true;
            }
        }
        return false;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }
}
