package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelContact {

    private long timestamp;
    private String contact;
    private String Photo;
    private String username;
    private String neo;

    public ModelContact() {

    }

    public ModelContact(String Photo, long timestamp, String contact, String username, String neo) {
        this.Photo = Photo;
        this.timestamp = timestamp;
        this.contact = contact;
        this.username = username;
        this.neo = neo;
    }

    public String getNeo() {
        return neo;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
