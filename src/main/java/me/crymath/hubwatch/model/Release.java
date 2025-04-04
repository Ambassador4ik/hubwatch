package me.crymath.hubwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Release {
    private long id;

    @JsonProperty("tag_name")
    private String tagName;

    private String name;
    private String body;

    @JsonProperty("draft")
    private boolean isDraft;

    @JsonProperty("prerelease")
    private boolean isPreRelease;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("published_at")
    private Date publishedAt;
}
