import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class StatementPrinter {

  public String print(Invoice invoice, HashMap<String, Play> plays) {
    double totalAmount = 0.0;
    int volumeCredits = 0;
    StringBuilder result = new StringBuilder();
    Customer customer = invoice.getCustomer(); // Get the customer from the invoice

    result.append(String.format("Statement for %s\n", customer.getName())); // Use customer's name

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.getPerformances()) { // Use the invoice's getPerformances method
      Play play = plays.get(perf.getPlayID()); // Use the invoice's getPlayID method
      double thisAmount = 0.0;

      switch (play.getType()) { // Use the play's getType method
        case TRAGEDY:
          thisAmount = 400.00;
          if (perf.getAudience() > 30) {
            thisAmount += 10.00 * (perf.getAudience() - 30);
          }
          break;
        case COMEDY:
          thisAmount = 300.00;
          if (perf.getAudience() > 20) {
            thisAmount += 100.00 + 5.00 * (perf.getAudience() - 20);
          }
          thisAmount += 3.00 * perf.getAudience();
          break;
        default:
          throw new Error("unknown type: " + play.getType());
      }

      volumeCredits += Math.max(perf.getAudience() - 30, 0);
      if (PlayType.COMEDY.equals(play.getType())) {
        volumeCredits += Math.floor(perf.getAudience() / 5);
      }

      result.append(String.format("  %s: %s (%s seats)\n", play.getName(), currencyFormatter.format(thisAmount), perf.getAudience()));
      totalAmount += thisAmount;
    }

    int totalLoyaltyPoints = customer.getLoyaltyPoints(); // Get customer's loyalty points
    if (totalLoyaltyPoints > 150) {
      totalAmount -= 15.0; // Apply a discount of 15€
      customer.deductLoyaltyPoints(150); // Deduct 150 points
    }
    totalLoyaltyPoints = customer.getLoyaltyPoints();

    result.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount)));
    result.append(String.format("You earned %s credits\n", volumeCredits));
    result.append(String.format("Total loyalty points: %s\n", totalLoyaltyPoints)); // Include loyalty points

    return result.toString();
  }
}
