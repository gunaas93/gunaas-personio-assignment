package com.gunaas.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", indexes = {@Index(columnList = "user_name", unique = true, name = "user_name")})
public class UserEntity extends AbstractEntity<Long> {

    @Size(max = 255, min = 1, message = "user name must be less than or equal to '{max}'")
    @Column(name = "user_name", unique = true)
    private String userName;

    @Size(max = 1000, min = 1, message = "user password must be less than or equal to '{max}'")
    @Column(name = "password")
    private String encryptedPassword;

    public UserEntity(String userName, String encryptedPassword) {
        super();
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
    }

    public UserEntity() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(@NotNull String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}

