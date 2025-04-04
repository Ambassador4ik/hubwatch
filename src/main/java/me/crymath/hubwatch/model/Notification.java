package me.crymath.hubwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String id;
    private String reason;

    @JsonProperty("unread")
    private boolean isUnread;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("last_read_at")
    private Date lastReadAt;

    private NotificationSubject subject;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationSubject {
        private String title;
        private String url;
        private String type;
    }
}
