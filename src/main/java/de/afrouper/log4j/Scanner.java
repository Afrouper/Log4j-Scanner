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
import java.util.stream.Collectors;

public class Scanner {

    private static final String JNDI_LOOKUP_CLASS = "/log4j/core/lookup/JndiLookup.class";
    private static final String LOG4J2_POM = "META-INF/maven/org.apache.logging.log4j/log4j-core/pom.properties";

    private final ZipFileVisitor zipFileVisitor;

    private final Map<String, String> findings;

    public Scanner(File rootDir) {
        zipFileVisitor = new ZipFileVisitor(rootDir);
        findings = new HashMap<>();
    }

    public void scan() throws IOException {
        zipFileVisitor.scan(this::handleLog4j2, this::isZip);
    }

    public Map<String, String> getFindings() {
        return findings;
    }

    private void handleLog4j2(ZipElement zipElement) {
        try {
            if (LOG4J2_POM.equals(zipElement.getZipEntry().getName())) {
                MavenCoordinates coordinates = readLog4j2MavenCoordinates(zipElement.getZipEntryInputStream());
                if(isVulnerable(coordinates)) {
                    findings.put(createPath(zipElement.getFilePath()), coordinates.getVersion().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isVulnerable(MavenCoordinates coordinates) {
        Version version = coordinates.getVersion();
        return version.getMajor() == 2 && version.getMinor() < 17;
    }

    private String createPath(List<String> path) {
        return path.stream().collect(Collectors.joining(" -> "));
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
