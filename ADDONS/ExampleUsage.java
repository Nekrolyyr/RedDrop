import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExampleUsage {

    public static void main(String[] args) {

        // Load testsite
        String testsite = "";
        try {
            testsite = new String(Files.readAllBytes(Paths.get("teststring.txt")));
        } catch (Exception e) {
            System.out.println(e);
        }

        // Initialize parser and parse!
        DonationListParser dlp = new DonationListParser();
        List<DonationListEntry> donListEntires = dlp.parse(testsite);
    }
}
