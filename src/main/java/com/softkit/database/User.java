package com.softkit.database;

import com.softkit.vo.City;
import com.softkit.vo.EnglishLevel;
import com.softkit.vo.Experience;
import com.softkit.vo.Step;
import lombok.*;
import org.hibernate.validator.constraints.Range;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer id;

    //    todo rename
    @Column(length = 100)
    private String name;

    @Column(length = 25)
    private String phone;

    @Column(length = 150)
    private String resumeFileId;

    @Column(length = 150)
    private String referralLink;

    @Range(min = 15, max = 99)
    private Integer age;

    private Integer userLocation;

    @Range (min = 99, max = 99999 )
    private Integer salaryFrom;

    @Range (min = 99, max = 99999 )
    private Integer salaryUpTo;

    @Enumerated(EnumType.ORDINAL)
    private Step step;

    @Enumerated(EnumType.ORDINAL)
    private EnglishLevel englishLevel;

    @Enumerated(EnumType.ORDINAL)
    private Experience experience;

    @Enumerated(EnumType.ORDINAL)
    private City city;

    private Timestamp registrationTimestamp;

    private Timestamp dataEditTimestamp;

    public User(Integer telegramId ) {
        this.id = telegramId;
    }
}