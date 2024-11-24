import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {

            FileReader fileReader = new FileReader("testinput.txt");
            MPLexer lexer = new MPLexer(fileReader);


            SyntaxTable parser = new SyntaxTable();


            boolean result = parser.SA_LL1(lexer);

            if (result) {
                System.out.println("Parsiranje je uspešno završeno!");
            } else {
                System.out.println("Došlo je do sintaksne greške.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fajl nije pronađen: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Greška prilikom čitanja fajla: " + e.getMessage());
        }
    }
}
