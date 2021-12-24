package de.afrouper.log4j;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Log4jScanner {

    public static void main(String[] args) throws IOException {
        if(args == null ||args.length != 1) {
            System.out.println("Usage: You have to specify a directory.");
            return;
        }
        File path = new File(args[0]);
        Scanner scanner = new Scanner(path);
        scanner.scan();
        Map<String, ScanResult> findings = scanner.getFindings();

        if(findings.isEmpty()) {
            System.out.println("No Findings!");
            System.exit(0);
        }
        findings.entrySet().forEach(e -> {
            ScanResult scanResult = e.getValue();
            scanResult.isVulnerable().ifPresent(vulnerable -> {
                if(vulnerable) {
                    System.out.println("[*] \uD83D\uDD25 " + e.getValue().getMavenCoordinates() + " in " + e.getKey());
                }
                /*
                if(scanResult.isPotentiallyVulnerable()) {
                    System.out.println("[?] ⁉️ " + e.getValue().getMavenCoordinates() + " in " + e.getKey());
                }
                */
            });
        });
        System.exit(1);
    }
}
