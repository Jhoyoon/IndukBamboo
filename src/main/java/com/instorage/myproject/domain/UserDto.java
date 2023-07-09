package com.instorage.myproject.domain;

import java.util.Objects;
import java.time.*;
public class UserDto{
    private String id;
    private String pwd;
    private String nickname;
    private LocalDateTime reg_date;

    public LocalDateTime getReg_date() {
        return reg_date;
    }

    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }

    public UserDto(){}

    public UserDto(String id, String pwd, String nickname) {
        this.id = id;
        this.pwd = pwd;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(pwd, userDto.pwd) && Objects.equals(nickname, userDto.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pwd, nickname);
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
}
//public class UserDto {
//
//    private String id;
//
//
//    private String pwd;
//
//
//    private String nickname;
//
//
//    private String email;
//
//    private String birth;
//    private LocalDateTime birthFinal;
//
//    public String getbirth() {
//        return birth;
//    }
//
//    public void setBirth(String birth) {
//        this.birth = birth;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserDto userDto = (UserDto) o;
//        return Objects.equals(id, userDto.id) && Objects.equals(pwd, userDto.pwd) && Objects.equals(nickname, userDto.nickname) && Objects.equals(email, userDto.email) && Objects.equals(birth, userDto.birth);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, pwd, nickname, email, birth);
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id='" + id + '\'' +
//                ", pwd='" + pwd + '\'' +
//                ", nickname='" + nickname + '\'' +
//                ", email='" + email + '\'' +
//                ", birth=" + birth +
//                '}';
//    }
//
//    public UserDto(){}
//
//    public UserDto(String id, String pwd, String nickname, String email, String birth) {
//        this.id = id;
//        this.pwd = pwd;
//        this.nickname = nickname;
//        this.email = email;
//        this.birth = birth;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public LocalDateTime getBirthFinal() {
//        return birthFinal;
//    }
//
//    public void setBirthFinal(LocalDateTime birthFinal) {
//        this.birthFinal = birthFinal;
//    }
//}