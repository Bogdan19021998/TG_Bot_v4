package database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int experienceId;

    private String experienceDescription;

    public UserExperience() {
    }

    public UserExperience( String experienceDescription ) {
        this.experienceDescription = experienceDescription;
    }

    public int getExperienceId() {
        return experienceId;
    }

    public String getExperienceDescription() {
        return experienceDescription;
    }

    public void setExperienceDescription(String experienceDescription) {
        this.experienceDescription = experienceDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserExperience)) return false;
        UserExperience that = (UserExperience) o;
        return experienceId == that.experienceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceId);
    }
}
