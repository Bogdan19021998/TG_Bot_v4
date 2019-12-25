package com.softkit.database;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
public class Date {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(value = AccessLevel.NONE )
    private Date createDate;
}
