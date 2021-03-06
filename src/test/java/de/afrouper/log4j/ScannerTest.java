package de.afrouper.log4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScannerTest {

    @Test
    public void testLog4jScanner() throws Exception {
        Scanner scanner = new Scanner(new File(getTestFiles()));
        scanner.scan();
        Map<String, ScanResult> findings = scanner.getFindings();
        Assertions.assertEquals(15, findings.size());

        List<Map.Entry<String, ScanResult>> vulEntries = findings.entrySet().stream().filter(e -> e.getValue().isVulnerable().orElse(false)).collect(Collectors.toList());
        Assertions.assertEquals(9, vulEntries.size());
    }

    private String getTestFiles() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "log4j2");
        return resourceDirectory.toFile().getAbsolutePath();
    }
}
