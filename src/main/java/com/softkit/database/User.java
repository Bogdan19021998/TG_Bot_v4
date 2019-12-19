package com.softkit.database;

import com.softkit.vo.Step;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
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

//    @OneToMany( cascade = CascadeType.ALL )
//    @JoinColumn(name="user_default_specializations_specialization_id", referencedColumnName = "specialization_id")
//    @JoinColumn(name="user_default_specializations_specialization_id")
//    @ElementCollectio
//    Set<UserDefaultSpecializations> userDefaultSpecializations;

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

    public User( Integer userId) {
        this.userId = userId;
        setStep( Step.getStepById(1) );
    }
}
