package de.afrouper.zip;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipFileVisitorTest {

    @Test
    public void testInternalResources() throws Exception {
        String testFilesDirectory = getTestFiles();
        ZipFileVisitor zw = new ZipFileVisitor(new File(testFilesDirectory));
    }

    private String getTestFiles() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "log4j2");
        return resourceDirectory.toFile().getAbsolutePath();
    }

    private Boolean isZip(String name) {
        String lowerCase = name.toLowerCase();
        return lowerCase.endsWith(".jar")
                || lowerCase.endsWith(".war")
                || lowerCase.endsWith(".ear")
                || lowerCase.endsWith(".zip");
    }
}
