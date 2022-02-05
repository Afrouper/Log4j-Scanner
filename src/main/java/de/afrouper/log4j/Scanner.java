package de.afrouper.log4j;

import de.afrouper.zip.ZipElement;
import de.afrouper.zip.ZipFileVisitor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Scanner {

    private static final String JNDI_LOOKUP_CLASS = "/log4j/core/lookup/JndiLookup.class";
    private static final String LOG4J2_POM = "META-INF/maven/org.apache.logging.log4j/log4j-core/pom.properties";

    private final ZipFileVisitor zipFileVisitor;

    private final Map<String, ScanResult> findings;

    public Scanner(File rootDir) {
        zipFileVisitor = new ZipFileVisitor(rootDir);
        findings = new HashMap<>();
    }

    public void scan() throws IOException {
        zipFileVisitor.scan(this::handleLog4j2, this::isZip);
    }

    public Map<String, ScanResult> getFindings() {
        return findings;
    }

    private void handleLog4j2(ZipElement zipElement) {
        try {
            if (LOG4J2_POM.equals(zipElement.getZipEntry().getName())) {
                ScanResult scanResult = getScanResult(createPath(zipElement.getFilePath()));
                MavenCoordinates coordinates = readLog4j2MavenCoordinates(zipElement.getZipEntryInputStream());
                scanResult.setMavenCoordinates(coordinates);
                if (isVulnerable(coordinates)) {
                    scanResult.setVulnerable(true);
                } else {
                    scanResult.setVulnerable(false);
                }
            }
            if (zipElement.getZipEntry().getName().endsWith(JNDI_LOOKUP_CLASS)) {
                ScanResult scanResult = getScanResult(createPath(zipElement.getFilePath()));
                scanResult.setPotentiallyVulnerable(true);
            }
        } catch (IOException e) {
            System.err.println("Unable to process ZIP element " + zipElement + ": " + e.getMessage());
        }
    }

    private ScanResult getScanResult(String key) {
        ScanResult scanResult = findings.get(key);
        if (scanResult == null) {
            scanResult = new ScanResult();
            findings.put(key, scanResult);
        }
        return scanResult;
    }

    private boolean isVulnerable(MavenCoordinates coordinates) {
        Version version = coordinates.getVersion();
        if (version.getPatchLevelNr() >= 0) {
            return version.getMajor() == 2 && version.getMinor() <= 17 && version.getPatchLevelNr() < 1;
        } else {
            return version.getMajor() == 2 && version.getMinor() <= 17;
        }
    }

    private String createPath(List<String> path) {
        return String.join(" -> ", path);
    }

    private MavenCoordinates readLog4j2MavenCoordinates(InputStream zipEntryInputStream) throws IOException {
        Properties props = new Properties();
        props.load(zipEntryInputStream);

        return new MavenCoordinates(props.getProperty("groupId"), props.getProperty("artifactId"), new Version(props.getProperty("version")));
    }

    private Boolean isZip(String name) {
        String lowerCase = name.toLowerCase();
        return lowerCase.endsWith(".jar")
                || lowerCase.endsWith(".war")
                || lowerCase.endsWith(".ear")
                || lowerCase.endsWith(".zip");
    }
}
