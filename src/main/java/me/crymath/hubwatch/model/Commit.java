package me.crymath.hubwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commit {
    private String sha;
    private CommitDetail commit;

    @JsonProperty("html_url")
    private String htmlUrl;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitDetail {
        private String message;
        private Author author;
        private Author committer;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Author {
            private String name;
            private String email;

            @JsonProperty("date")
            private Date date;
        }
    }
}
