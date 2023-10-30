import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {

        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  Play.createPlay("Hamlet", "tragedy"));
        plays.put("as-like",  Play.createPlay("As You Like It", "comedy"));
        plays.put("othello",  Play.createPlay("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        verify(result);
    }
    @Test
    void testAudience30ForTragedy() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("playID", Play.createPlay("Play Name", "tragedy"));

        Invoice invoice = new Invoice("Customer Name", List.of(new Performance("playID", 30)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        String expectedStatement = "Statement for Customer Name\n" +
                "  Play Name: $400.00 (30 seats)\n" +
                "Amount owed is $400.00\n" +
                "You earned 0 credits\n";

        assertEquals(expectedStatement, result);
    }
    @Test
    void testAudience20ForComedy() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("playID", Play.createPlay("Play Name", "comedy"));

        Invoice invoice = new Invoice("Customer Name", List.of(new Performance("playID", 20)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        String expectedStatement = "Statement for Customer Name\n" +
                "  Play Name: $360.00 (20 seats)\n" +
                "Amount owed is $360.00\n" +
                "You earned 4 credits\n"; // Mise à jour de cette ligne en fonction du comportement attendu
        assertEquals(expectedStatement, result);
    }
    /*@Test
    void statementWithNewPlayTypes() {

        HashMap<String, Play> plays = new HashMap<>();
        Assertions.assertThrows(Error.class, () -> {
            plays.put("henry-v", Play.createPlay("Henry V", "history"));
            plays.put("as-like", Play.createPlay("As You Like It", "pastoral"));
        });
        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));

        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
    }*/
}
