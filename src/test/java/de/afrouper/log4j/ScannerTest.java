package de.afrouper.log4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ScannerTest {

    @Test
    public void testLog4jScanner() throws Exception {
        Scanner scanner = new Scanner(new File(getTestFiles()));
        scanner.scan();
        Map<String, String> findings = scanner.getFindings();
        Assertions.assertEquals(11, findings.size());
    }

    private String getTestFiles() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "log4j2");
        return resourceDirectory.toFile().getAbsolutePath();
    }
}
