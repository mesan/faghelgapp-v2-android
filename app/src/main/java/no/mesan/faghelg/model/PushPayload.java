package no.mesan.faghelg.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PushPayload implements Parcelable {

	private String title;
	private String content;

	public PushPayload() {
	}

	public PushPayload(String title, String content) {
		super();
		this.title = title;
		this.content = content;
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

	protected PushPayload(Parcel in) {
		title = in.readString();
		content = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(content);
	}

	public static final Parcelable.Creator<PushPayload> CREATOR = new Parcelable.Creator<PushPayload>() {
		@Override
		public PushPayload createFromParcel(Parcel in) {
			return new PushPayload(in);
		}

		@Override
		public PushPayload[] newArray(int size) {
			return new PushPayload[size];
		}
	};
}
