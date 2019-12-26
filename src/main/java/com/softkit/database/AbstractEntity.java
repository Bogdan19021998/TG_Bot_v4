package com.softkit.database;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
@NoArgsConstructor
public class AbstractEntity {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer userId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(value = AccessLevel.NONE )
    @Getter
    private Date DateCreated;
}
