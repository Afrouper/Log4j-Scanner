package de.afrouper.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility for traversing all ZIP files (JAR, ZIP, WAR, EAR) in a directory. The visitor also walks through nested entries (e.g. FAT JARs)
 */
public class ZipFileVisitor {

    private final File rootDir;

    /**
     * Creates a new Instance visiting all ZIPs in a directory. Subdirectories are also scanned.
     *
     * @param rootDir Directory starting  the scan
     */
    public ZipFileVisitor(File rootDir) {
        Objects.requireNonNull(rootDir, "Root directory cannot be null.");
        if (!rootDir.exists()) {
            throw new IllegalArgumentException("Path " + rootDir.getAbsolutePath() + " does not exists.");
        }
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException("Path " + rootDir.getAbsolutePath() + " is no directory.");
        }
        if (!rootDir.canRead()) {
            throw new IllegalArgumentException("Path " + rootDir.getAbsolutePath() + " is not readable.");
        }
        this.rootDir = rootDir;
    }

    /**
     * Scans the root directory. For each element in the ZIP files the consumer is called.
     *
     * @param zipElementConsumer {@link Consumer} handling ZIP entries
     * @throws IOException Error reading ZIPs entries
     */
    public void scan(Consumer<ZipElement> zipElementConsumer, Function<String, Boolean> acceptZip) throws IOException {
        scan(rootDir, zipElementConsumer, acceptZip);
    }

    private void scan(File dir, Consumer<ZipElement> zipElementConsumer, Function<String, Boolean> acceptZip) throws IOException {
        for (File file : Objects.requireNonNull(dir.listFiles())) {

            if (file.isFile() && acceptZip.apply(file.getName())) {
                checkZip(file, zipElementConsumer, acceptZip);
            } else if (file.isDirectory()) {
                scan(file, zipElementConsumer, acceptZip);
            }
        }
    }

    private void checkZip(File file, Consumer<ZipElement> zipElementConsumer, Function<String, Boolean> acceptZip) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
            List<String> fileNames = new ArrayList<>();
            fileNames.add(file.getAbsolutePath());
            processZipFile(fileNames, zipInputStream, zipElementConsumer, acceptZip);
        }
    }

    private void processZipFile(List<String> fileNames, ZipInputStream zipInputStream, Consumer<ZipElement> zipElementConsumer, Function<String, Boolean> acceptZip) throws IOException {
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (acceptZip.apply(zipEntry.getName())) {
                ZipInputStream nestedZis = new ZipInputStream(zipInputStream);
                fileNames.add(zipEntry.getName());
                processZipFile(fileNames, nestedZis, zipElementConsumer, acceptZip);
                fileNames.remove(fileNames.size() - 1);
            } else {
                zipElementConsumer.accept(new ZipElement(fileNames, zipEntry, zipInputStream));
            }
        }
    }
}
