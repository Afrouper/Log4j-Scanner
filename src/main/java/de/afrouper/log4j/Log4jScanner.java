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
        Map<String, String> findings = scanner.getFindings();

        if(findings.isEmpty()) {
            System.out.println("No Findings!");
            System.exit(0);
        }
        findings.entrySet().stream().forEach(e -> System.out.println("\uD83D\uDD25: version " + e.getValue() + " in " + e.getKey()));
    }
}
