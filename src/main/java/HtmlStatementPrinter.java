import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class HtmlStatementPrinter {

    public String print(Invoice invoice, HashMap<String, Play> plays) {
        double totalAmount = 0.0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder();
        Customer customer = invoice.getCustomer();

        result.append("<html><head><title>Invoice</title></head><body>");
        result.append("<style>");
        result.append("table { border-collapse: collapse; width: 50%; }");
        result.append("th, td { border: 2px double black; padding: 8px; text-align: center; }");
        result.append("th { border: 2px double black; padding: 8px; text-align: center; background-color: #f2f2f2; }");
        result.append("b { font-weight: bold; }");
        result.append(".align-right { text-align: right; }");
        result.append(".price { text-align: center; }");
        result.append("</style>");
        result.append("<h1>Invoice</h1>");

        result.append("<p><b>Client:</b> " + invoice.customer.getName() + "</p>");

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        result.append("<table>");
        result.append("<tr>");
        result.append("<th colspan='3'>Piece</th>");
        result.append("<th colspan='3'>Seats Sold</th>");
        result.append("<th colspan='3' class='price'>Price</th>");
        result.append("</tr>");

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = 0.0;

            switch (play.type) {
                case TRAGEDY:
                    thisAmount = 400.00;
                    if (perf.audience > 30) {
                        thisAmount += 10.00 * (perf.audience - 30);
                    }
                    break;
                case COMEDY:
                    thisAmount = 300.00;
                    if (perf.audience > 20) {
                        thisAmount += 100.00 + 5.00 * (perf.audience - 20);
                    }
                    thisAmount += 3.00 * perf.audience;
                    break;
                default:
                    throw new Error("unknown type: " + play.type);
            }

            volumeCredits += Math.max(perf.audience - 30, 0);
            if (PlayType.COMEDY.equals(play.type)) {
                volumeCredits += Math.floor(perf.audience / 5);
            }



            result.append("<tr>");
            result.append("<td colspan='3'>" + play.name + "</td>");
            result.append("<td colspan='3'>" + perf.audience + "</td>");
            result.append("<td colspan='3' class='price'>" + currencyFormatter.format(thisAmount) + "</td>"); // Appliquez la classe 'price' pour centrer les prix
            result.append("</tr>");
            totalAmount += thisAmount;
        }
        int totalLoyaltyPoints = customer.getLoyaltyPoints(); // Get customer's loyalty points
        if (totalLoyaltyPoints > 150) {
            double discount = 15.0; // Apply a discount of 15€
            totalAmount -= discount;
            customer.deductLoyaltyPoints(150); // Deduct 150 points
        }
        totalLoyaltyPoints = customer.getLoyaltyPoints();


        result.append("<tr>");
        result.append("<td colspan='6' class='align-right'><b >Totlowned</b></td>");
        result.append("<td colspan='3' >" + currencyFormatter.format(totalAmount) + "</td>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td colspan='6' class='align-right'><b >Fidelity Points earned</b></td>");
        result.append("<td colspan='3' >" + volumeCredits + "</td>");
        result.append("</tr>");

        result.append("<tr>");
        result.append("<td colspan='6' class='align-right'><b>Total Loyalty Points</b></td>");
        result.append("<td colspan='3' class='price'>" + totalLoyaltyPoints + "</td>");
        result.append("</tr>");

        result.append("</table>");
        result.append("<p>Payment is required under 30 days. We can break your knees if you don't do so.</p>");

        result.append("</body></html>");

        return result.toString();
    }
}
