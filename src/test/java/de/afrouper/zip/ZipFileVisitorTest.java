package de.afrouper.zip;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipFileVisitorTest {

    @Test
    public void testInternalResources() throws Exception {
        String testFilesDirectory = getTestFiles("log4j2");
        ZipFileVisitor zw = new ZipFileVisitor(new File(testFilesDirectory));
        zw.scan(c -> System.out.println(c.getZipEntry().getName()), this::isZip);
    }

    private String getTestFiles(String dir) {
        Path resourceDirectory = Paths.get("src", "test", "resources", dir);
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
