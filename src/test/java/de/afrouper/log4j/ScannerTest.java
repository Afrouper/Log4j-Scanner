package de.afrouper.log4j;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ScannerTest {

    @Test
    public void testLog4jScanner() throws Exception {
        Scanner scanner = new Scanner(new File(getTestFiles("log4j2")));
        scanner.scan();
        Map<String, String> findings = scanner.getFindings();
        findings.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    private String getTestFiles(String dir) {
        Path resourceDirectory = Paths.get("src", "test", "resources", dir);
        return resourceDirectory.toFile().getAbsolutePath();
    }
}
