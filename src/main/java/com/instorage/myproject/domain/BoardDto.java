package com.instorage.myproject.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;
@Repository
public class BoardDto {
    private String type;
    private int bno;
    private String writer;
    private String nickname;
    private String title;
    private String content;
    private LocalDate reg_date;
    private int view_cnt;
    private int comment_cnt;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDto boardDto = (BoardDto) o;
        return bno == boardDto.bno && view_cnt == boardDto.view_cnt && comment_cnt == boardDto.comment_cnt && Objects.equals(type, boardDto.type) && Objects.equals(writer, boardDto.writer) && Objects.equals(title, boardDto.title) && Objects.equals(content, boardDto.content) && Objects.equals(reg_date, boardDto.reg_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, bno, writer, title, content, reg_date, view_cnt, comment_cnt);
    }

    public BoardDto() {}

    public BoardDto(String type, String writer, String title, String content) {
        this.type = type;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getReg_date() {
        return reg_date;
    }

    public void setReg_date(LocalDate reg_date) {
        this.reg_date = reg_date;
    }

    public int getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(int view_cnt) {
        this.view_cnt = view_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "bno=" + bno +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", reg_date=" + reg_date +
                ", view_cnt=" + view_cnt +
                ", comment_cnt=" + comment_cnt +
                '}';
    }
}
