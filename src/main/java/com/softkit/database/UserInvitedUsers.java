package com.softkit.database;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class UserInvitedUsers {

    @Id
    @Column( unique = true )
    private int telegramId;
/*
    @Type( type = "int-array" )
    @Column(
            columnDefinition = "integer[]"
    )
    private int[] invitedUsers;
*/
    private Timestamp dateAdded;

    public UserInvitedUsers(){
    }
/*
    public UserInvitedUsers(int[] invitedUsers ) {
        this.invitedUsers = invitedUsers;
    }
*/
    public int getTelegramId() {
        return telegramId;
    }
/*
    public int[] getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(int[] invitedUsers) {
        this.invitedUsers = invitedUsers;
    }
*/
    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInvitedUsers)) return false;
        UserInvitedUsers that = (UserInvitedUsers) o;
        return telegramId == that.telegramId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId);
    }
}
