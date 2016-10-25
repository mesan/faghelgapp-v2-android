package no.mesan.faghelg.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private Person author;
    private String text;
    private String imageUrl;
    private String timestamp;

    public Message() {

    }

}
