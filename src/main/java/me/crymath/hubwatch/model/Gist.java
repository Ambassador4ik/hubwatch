package me.crymath.hubwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gist {
    private String id;
    private String description;

    @JsonProperty("public")
    private boolean isPublic;

    private User owner;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
    // Files represented as a Map: filename -> file metadata
    private Map<String, GistFile> files;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GistFile {
        private String filename;
        private String type;
        private String language;
        private long size;

        @JsonProperty("raw_url")
        private String rawUrl;
    }
}
