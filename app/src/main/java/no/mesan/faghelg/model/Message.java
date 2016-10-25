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

    public String getSenderImageUrl(){
        return "https://s3-eu-west-1.amazonaws.com/faghelg/" + this.sender + ".png";
    }
}
