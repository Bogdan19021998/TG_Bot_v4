package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class UserLocation {

    @Id
    @Column( unique = true )
    private int telegramId;

    private float latitude;
    private float longitude;
    private Timestamp dateAdded;

    public UserLocation() {
    }

    public UserLocation( float latitude, float longitude, long dateAdded) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateAdded = new Timestamp( dateAdded );
    }

    public int getTelegramId() {
        return telegramId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLocation)) return false;
        UserLocation that = (UserLocation) o;
        return telegramId == that.telegramId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId);
    }
}
