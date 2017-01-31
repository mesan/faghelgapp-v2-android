package no.mesan.faghelg.model;

public class PushDevice {
    private String owner;
    private String token;
    private String os;
    public PushDevice() {
    }
    public PushDevice(String owner, String token, String os) {
        this.owner = owner;
        this.token = token;
        this.os = os;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
}