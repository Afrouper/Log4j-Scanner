package de.afrouper.log4j;

import java.util.Optional;

public class ScanResult {

    private MavenCoordinates mavenCoordinates;
    private Boolean vulnerable;
    private boolean potentiallyVulnerable;

    ScanResult() {
    }

    public MavenCoordinates getMavenCoordinates() {
        return mavenCoordinates;
    }

    public void setMavenCoordinates(MavenCoordinates mavenCoordinates) {
        this.mavenCoordinates = mavenCoordinates;
    }

    public Optional<Boolean> isVulnerable() {
        return Optional.ofNullable(vulnerable);
    }

    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean isPotentiallyVulnerable() {
        return potentiallyVulnerable;
    }

    public void setPotentiallyVulnerable(boolean potentiallyVulnerable) {
        this.potentiallyVulnerable = potentiallyVulnerable;
    }

    @Override
    public String toString() {
        return mavenCoordinates +
                ", vulnerable=" + vulnerable +
                ", potentiallyVulnerable=" + potentiallyVulnerable;
    }
}
