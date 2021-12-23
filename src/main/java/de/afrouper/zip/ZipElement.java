package de.afrouper.zip;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;

/**
 * Element representing an ZIP entry.
 */
public final class ZipElement {

    private final List<String> filePath;
    private final ZipEntry zipEntry;
    private final InputStream zipEntryInputStream;

    ZipElement(List<String> filePath, ZipEntry zipEntry, InputStream zipEntryInputStream) {
        this.filePath = filePath;
        this.zipEntry = zipEntry;
        this.zipEntryInputStream = zipEntryInputStream;
    }

    /**
     * Getter for the current {@link ZipEntry}
     *
     * @return The underling entry in the ZIP file
     */
    public ZipEntry getZipEntry() {
        return zipEntry;
    }

    /**
     * An {@link InputStream} for reading the content of this ZIP entry. The stream should not be closed.
     *
     * @return The stream to read the entries content from
     */
    public InputStream getZipEntryInputStream() {
        return new DoNotCloseInputStream(zipEntryInputStream);
    }

    /**
     * If there are nested ZIP files (e.g. WARs, FAT JARs or Uber-JARs) for each element there is an entry in the {@link List}.
     * The First entry is the absolute path.
     *
     * @return List for each entry.
     */
    public List<String> getFilePath() {
        return Collections.unmodifiableList(filePath);
    }
}
