import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        SubstringFinder finder = new SubstringFinder();

        try (InputStream stream =
                     Files.newInputStream(Path.of("example.txt"))) {

            List<Long> indices =
                    finder.findOccurrences(stream, "example");

            System.out.println(indices);
        }
    }
}