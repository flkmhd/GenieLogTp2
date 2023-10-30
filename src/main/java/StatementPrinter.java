import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public String print(Invoice invoice, HashMap<String, Play> plays) {
    double totalAmount = 0.0;
    int volumeCredits = 0;
    StringBuilder result = new StringBuilder();
    result.append(String.format("Statement for %s\n", invoice.customer));

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      double thisAmount = 0.0;
      switch (play.type) {
        case TRAGEDY:
          thisAmount = 400.00; // Montant de base pour une tragédie en dollars avec des centimes
          if (perf.audience > 30) {
            thisAmount += 10.00 * (perf.audience - 30); // Ajouter 10 dollars par siège au-delà de 30
          }
          break;
        case COMEDY:
          thisAmount = 300.00; // Montant de base pour une comédie en dollars avec des centimes
          if (perf.audience > 20) {
            thisAmount += 100.00 + 5.00 * (perf.audience - 20); // Ajouter 100 dollars plus 5 dollars par siège au-delà de 20
          }
          thisAmount += 3.00 * perf.audience; // Ajouter 3 dollars par siège
          break;
        default:
          throw new Error("unknown type: ${play.type}");
      }

      // add volume credits
      volumeCredits += Math.max(perf.audience - 30, 0);
      // add extra credit for every ten comedy attendees
      if (PlayType.COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // Ajouter une ligne au résultat avec le nom de la pièce, le montant et le nombre de sièges
      result.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount), perf.audience));
      totalAmount += thisAmount; // Accumuler le montant total
    }
    // Ajouter le montant total dû et les crédits gagnés au résultat
    result.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount)));
    result.append(String.format("You earned %s credits\n", volumeCredits));

    return result.toString(); // Retourner le résultat complet sous forme de chaîne;
  }

}
