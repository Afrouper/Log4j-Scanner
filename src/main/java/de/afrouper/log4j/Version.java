package de.afrouper.log4j;

import java.util.Objects;

public class Version {

    private final int major;
    private final int minor;
    private final String patchLevel;

    public Version(String versionString) {
        Objects.requireNonNull(versionString);
        String[] parts = versionString.split("\\.");
        if(parts.length == 3) {
            this.major = Integer.parseInt(parts[0]);
            this.minor = Integer.parseInt(parts[1]);
            this.patchLevel = parts[2];
        }
        else {
            throw new IllegalArgumentException("Invalid version '" + versionString + "'. Version must be contain Major.Minor.PatchLevel!");
        }
    }
    public Version(int major, int minor, String patchLevel) {
        Objects.requireNonNull(patchLevel, "Patch-level cannot be null");
        this.major = major;
        this.minor = minor;
        this.patchLevel = patchLevel;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public String getPatchLevel() {
        return patchLevel;
    }

    public int getPatchLevelNr() {
        try {
            if(getPatchLevel() != null) {
                return Integer.parseInt(getPatchLevel());
            }
            else {
                return -1;
            }
        }
        catch(Exception e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patchLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return major == version.major && minor == version.minor && patchLevel.equals(version.patchLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patchLevel);
    }
}
