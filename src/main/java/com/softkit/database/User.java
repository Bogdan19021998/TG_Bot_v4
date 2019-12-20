package com.softkit.database;

import com.softkit.vo.Step;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_table")
@Data
@NoArgsConstructor
@NotNull
@EqualsAndHashCode
public class User {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer userId;

    @Enumerated(EnumType.ORDINAL)
    private Step step;
    private String candidate;
    private Integer age;
    private Integer experience;
    private Integer englishLevel;
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

    public User( Integer userId ) {
        this.userId = userId;
        setStep(Step.START);
    }
}