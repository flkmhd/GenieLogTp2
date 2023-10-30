import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.*;

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
    void exampleHtmlStatement() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  Play.createPlay("Hamlet", "tragedy"));
        plays.put("as-like",  Play.createPlay("As You Like It", "comedy"));
        plays.put("othello",  Play.createPlay("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));


        HtmlStatementPrinter htmlPrinter = new HtmlStatementPrinter();
        String htmlResult= htmlPrinter.print(invoice, plays);

        // Utilisez ApprovalTests pour approuver le rendu HTML
        verifyHtml(htmlResult);
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
    void testAudience30ForTragedyhtml() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("playID", Play.createPlay("Play Name", "tragedy"));

        Invoice invoice = new Invoice("Customer Name", List.of(new Performance("playID", 30)));

        HtmlStatementPrinter htmlPrinter = new HtmlStatementPrinter();
        String htmlResult= htmlPrinter.print(invoice, plays);

        String expectedStatement = "<html><head><title>Invoice</title></head><body><style>table { border-collapse: collapse; width: 50%; }th, td { border: 2px double black; padding: 8px; text-align: center; }th { border: 2px double black; padding: 8px; text-align: center; background-color: #f2f2f2; }b { font-weight: bold; }.align-right { text-align: right; }.price { text-align: center; }</style><h1>Invoice</h1><p><b>Client:</b> Customer Name</p><table><tr><th colspan='3'>Piece</th><th colspan='3'>Seats Sold</th><th colspan='3' class='price'>Price</th></tr><tr><td colspan='3'>Play Name</td><td colspan='3'>30</td><td colspan='3' class='price'>$400.00</td></tr><tr><td colspan='6' class='align-right'><b >Totlowned</b></td><td colspan='3' >$400.00</td></tr><tr><td colspan='6' class='align-right'><b >Fidelity Points earned</b></td><td colspan='3' >0</td></tr></table><p>Payment is required under 30 days. We can break your knees if you don't do so.</p></body></html>";

        assertEquals(expectedStatement, htmlResult);
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
    @Test
    void testAudience20ForComedyhtml() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("playID", Play.createPlay("Play Name", "comedy"));

        Invoice invoice = new Invoice("Customer Name", List.of(new Performance("playID", 20)));

        HtmlStatementPrinter htmlPrinter = new HtmlStatementPrinter();
        String htmlResult= htmlPrinter.print(invoice, plays);


        String expectedStatement = "<html><head><title>Invoice</title></head><body><style>table { border-collapse: collapse; width: 50%; }th, td { border: 2px double black; padding: 8px; text-align: center; }th { border: 2px double black; padding: 8px; text-align: center; background-color: #f2f2f2; }b { font-weight: bold; }.align-right { text-align: right; }.price { text-align: center; }</style><h1>Invoice</h1><p><b>Client:</b> Customer Name</p><table><tr><th colspan='3'>Piece</th><th colspan='3'>Seats Sold</th><th colspan='3' class='price'>Price</th></tr><tr><td colspan='3'>Play Name</td><td colspan='3'>20</td><td colspan='3' class='price'>$360.00</td></tr><tr><td colspan='6' class='align-right'><b >Totlowned</b></td><td colspan='3' >$360.00</td></tr><tr><td colspan='6' class='align-right'><b >Fidelity Points earned</b></td><td colspan='3' >4</td></tr></table><p>Payment is required under 30 days. We can break your knees if you don't do so.</p></body></html>"; // Mise à jour de cette ligne en fonction du comportement attendu
        assertEquals(expectedStatement, htmlResult);
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
