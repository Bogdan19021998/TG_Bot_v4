package database;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employmentId;

    private String employmentDescription;

    public UserEmployment() {
    }

    public UserEmployment( int employmentId )
    {
        this.employmentId = employmentId;
    }

    public int getEmploymentId() {
        return employmentId;
    }

    public String getEmploymentDescription() {
        return employmentDescription;
    }

    public void setEmploymentDescription(String employmentDescription) {
        this.employmentDescription = employmentDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEmployment)) return false;
        UserEmployment that = (UserEmployment) o;
        return employmentId == that.employmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employmentId);
    }
}
