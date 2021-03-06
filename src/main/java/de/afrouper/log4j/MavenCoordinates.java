package de.afrouper.log4j;

import java.util.Objects;

public class MavenCoordinates {

    private final String groupId;
    private final String artifactId;
    private final Version version;

    public MavenCoordinates(String groupId, String artifactId, Version version) {
        Objects.requireNonNull(groupId);
        Objects.requireNonNull(artifactId);
        Objects.requireNonNull(version);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return groupId + ':' + artifactId + ':' + version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MavenCoordinates that = (MavenCoordinates) o;
        return groupId.equals(that.groupId) && artifactId.equals(that.artifactId) && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version);
    }
}
