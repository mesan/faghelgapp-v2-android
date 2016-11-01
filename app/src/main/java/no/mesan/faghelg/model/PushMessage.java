package no.mesan.faghelg.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PushMessage implements Parcelable {
	
	private long receivedTime;
	private String title;
	private String content;

	public PushMessage() {
		super();
	}

	public PushMessage(String title, String content, long receivedTime) {
		super();
		this.title = title;
		this.content = content;
		this.receivedTime = receivedTime;
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
	
	public long getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(long receivedTime) {
		this.receivedTime = receivedTime;
	}

	/**
	 * PARCELABLE	 
	 **/

	public PushMessage(Parcel in) {
		title = in.readString();
		content = in.readString();
		receivedTime = in.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(content);
		dest.writeLong(receivedTime);
	}

	public static final Parcelable.Creator<PushMessage> CREATOR = new Parcelable.Creator<PushMessage>() {
	    public PushMessage createFromParcel(Parcel in) {
	        return new PushMessage(in);
	    }

	    public PushMessage[] newArray(int size) {
	        return new PushMessage[size];
	    }
	};

}
