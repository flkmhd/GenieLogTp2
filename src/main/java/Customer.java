public class Customer {
    private String name;
    private String customerNumber;
    private int loyaltyPoints;

    public Customer(String name, String customerNumber,int loyaltyPoints) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.loyaltyPoints = loyaltyPoints; // Initialize loyalty points to zero
    }

    public String getName() {
        return name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }


    public void deductLoyaltyPoints(int points) {
        this.loyaltyPoints -= points;
    }
}
