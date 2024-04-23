package uppgift;

public class Vehicle {
    private String registrationNumber;

    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public int hashCode() {
        int hash = 17;  
        int primeMultiplier = 37;  

        for (int i = 0; i < registrationNumber.length(); i++) {
            int charValue = registrationNumber.charAt(i);
            hash = primeMultiplier * hash + charValue;
        }
        return hash;
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (this == objectToCompare) return true;
        if (objectToCompare == null || getClass() != objectToCompare.getClass()) return false;
        Vehicle vehicle = (Vehicle) objectToCompare;
        return registrationNumber.equals(vehicle.registrationNumber);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}


/*public class Vehicle {
  private String registrationNumber;

    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public int hashCode() {

        return registrationNumber.hashCode();
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (this == objectToCompare) return true;
        if (objectToCompare == null || getClass() != objectToCompare.getClass()) return false;
        Vehicle vehicle = (Vehicle) objectToCompare;
        return registrationNumber.equals(vehicle.registrationNumber);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}*/ 
