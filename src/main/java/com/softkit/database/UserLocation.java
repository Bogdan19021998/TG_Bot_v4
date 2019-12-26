package com.softkit.database;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NonNull
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserLocation {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer telegramId;

    @NotNull
    private Float longitude;
    private Float latitude;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(value = AccessLevel.NONE )
    @Getter
    private Date dateCreated;

    public UserLocation(Integer telegramId, Float longitude, Float latitude) {
        this.telegramId = telegramId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}