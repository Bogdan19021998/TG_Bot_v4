package com.softkit.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class UserEmployments {

    @Id
    @Column( unique = true )
    private Integer telegramId;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn( name="user_employments_telegram_id")
//    private Set<UserEmployment> userEmployments = new HashSet<>();

    public UserEmployments() {
    }

    public UserEmployments( Integer id )
    {
        this.telegramId = id;
    }


}
