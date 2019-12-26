package com.softkit.database;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserLocation {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer telegramId;

    private Float longitude;
    private Float latitude;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(value = AccessLevel.NONE )
    @Getter
    private Date DateCreated;
}