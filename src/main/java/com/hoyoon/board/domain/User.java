package com.hoyoon.board.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Objects;
import java.util.*;
import java.time.*;

public class User {

    private String id;


    private String pwd;


    private String nickname;


    private String email;
    
    private String birth;
    private LocalDate birthFinal;

    public String getbirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(pwd, user.pwd) && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(birth, user.birth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pwd, nickname, email, birth);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }

    public User(){}

    public User(String id, String pwd, String nickname, String email, String birth) {
        this.id = id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.email = email;
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthFinal() {
        return birthFinal;
    }

    public void setBirthFinal(LocalDate birthFinal) {
        this.birthFinal = birthFinal;
    }
}
