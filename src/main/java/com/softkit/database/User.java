package com.softkit.database;

import com.softkit.vo.Step;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="user_table")
@Data
@NoArgsConstructor
@NotNull
public class User {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    private Integer userId;

    @Enumerated(EnumType.ORDINAL)
    private Step step;
//    todo rename
    private String candidate;
    private Integer age;
    //    todo add enum value
    private Integer englishLevel;
    //    todo add enum value
    private Integer city;
    private Integer userLocation;
    private Integer salaryFrom;
    private Integer salaryUpTo;
    private String phone;
    private String resumeFileId;
    private String referralLink;
    private Timestamp registrationTimestamp;
    private Timestamp dataEditTimestamp;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserSpecialization> specializations = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserTechnology> technologies = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Referral> referrals = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserEmployment> employments = new HashSet<>();

    @OneToOne
//    todo add enum value
    private Experience experience;

    public User( Integer userId ) {
        this.userId = userId;
//        todo remove this
        setStep(Step.START);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}