package no.mesan.faghelg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.joda.time.DateTime;

import no.mesan.faghelg.util.CustomDateTimeDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private long id;
    private String title;
    private String content;
    private String sender;
    private String imageUrl;
    private DateTime timestamp;

    public Message() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderImageUrl() {
        return "https://s3-eu-west-1.amazonaws.com/faghelg/" + this.sender + ".png";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (title != null ? !title.equals(message.title) : message.title != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null)
            return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (imageUrl != null ? !imageUrl.equals(message.imageUrl) : message.imageUrl != null)
            return false;
        return timestamp != null ? timestamp.equals(message.timestamp) : message.timestamp == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
