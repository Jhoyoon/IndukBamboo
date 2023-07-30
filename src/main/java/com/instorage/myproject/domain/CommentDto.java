package com.instorage.myproject.domain;

import org.springframework.stereotype.Repository;

import java.util.*;

public class CommentDto {
    private Integer cno;
    private Integer bno;
    private Integer pcno;
    private String  comment;
    private String  commenter;
    private String nickname;
    private Date    reg_date;
    private Integer reply_cnt;
    private Integer deleted;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getReply_cnt() {
        return reply_cnt;
    }

    public void setReply_cnt(Integer reply_cnt) {
        this.reply_cnt = reply_cnt;
    }

    public CommentDto() {}
    public CommentDto(Integer bno,String comment,String commenter){
        this(bno,null,comment,commenter);
    }
    public CommentDto(Integer bno, Integer pcno, String comment, String commenter) {
        this.bno = bno;
        this.pcno = pcno;
        this.comment = comment;
        this.commenter = commenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(cno, that.cno) && Objects.equals(bno, that.bno) && Objects.equals(pcno, that.pcno) && Objects.equals(comment, that.comment) && Objects.equals(commenter, that.commenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cno, bno, pcno, comment, commenter);
    }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public Integer getPcno() {
        return pcno;
    }

    public void setPcno(Integer pcno) {
        this.pcno = pcno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Integer getCno() {
        return cno;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "cno=" + cno +
                ", bno=" + bno +
                ", pcno=" + pcno +
                ", comment='" + comment + '\'' +
                ", commenter='" + commenter + '\'' +
                ", reg_date=" + reg_date +
                '}';
    }
}