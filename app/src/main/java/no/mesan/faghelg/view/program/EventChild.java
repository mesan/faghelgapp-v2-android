package no.mesan.faghelg.view.program;

public class EventChild  {

    private String eventImage;
    private String profileImage;
    private String lecturerName;
    private String lecturerNick;
    private String eventDescription;

    public EventChild(String eventImage, String profileImage, String lecturerName, String lecturerNick, String eventDescription) {
        this.eventImage = eventImage;
        this.profileImage = profileImage;
        this.lecturerName = lecturerName;
        this.lecturerNick = lecturerNick;
        this.eventDescription = eventDescription;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerNick() {
        return lecturerNick;
    }

    public void setLecturerNick(String lecturerNick) {
        this.lecturerNick = lecturerNick;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
