package com.softkit.database;

import com.softkit.vo.Step;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created
 * 10.12.2019
 *
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter(value = AccessLevel.NONE )
@NotNull
@EqualsAndHashCode
public class UserStatus {

    @Id
  //  @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(unique = true)
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.ORDINAL)
    private Step step;

    private String statusDescription;
    @Column(length = 511)
    private String botMessage;
    private String userMistakeResponse;
}