package database;

import vo.Step;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class User {

    @Id
    @Column( unique = true )
    private int userId;

    private int statusId;
    private String candidate;

    private Step currentStep;

    private int age;
    private int experience;
    private int englishLevel;
    private int city;
    private int salaryFrom;
    private int salaryUpTo;
    private String phone;
    private String resumeFileId;
    private String referralLink;
    private Timestamp registrationTimestamp;
    private Timestamp dataEditTimestamp;

    public Step getCurrentStep() {
        return this.currentStep;
    }

    public User() {
        this.statusId = 0;
    }

    public long getUserId() {
        return userId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(int englishLevel) {
        this.englishLevel = englishLevel;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(int salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public int getSalaryUpTo() {
        return salaryUpTo;
    }

    public void setSalaryUpTo(int salaryUpTo) {
        this.salaryUpTo = salaryUpTo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResumeFileId() {
        return resumeFileId;
    }

    public void setResumeFileId(String resumeFileId) {
        this.resumeFileId = resumeFileId;
    }

    public String getReferralLink() {
        return referralLink;
    }

    public void setReferralLink(String referralLink) {
        this.referralLink = referralLink;
    }

    public Timestamp getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(Timestamp registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public Timestamp getDataEditTimestamp() {
        return dataEditTimestamp;
    }

    public void setDataEditTimestamp(Timestamp dataEditTimestamp) {
        this.dataEditTimestamp = dataEditTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
