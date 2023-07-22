package com.instorage.myproject.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;
@Repository
public class BoardDto {
    private int bno;
    private String writer;
    private String title;
    private String content;
    private LocalDate reg_date;
    private int view_cnt;
    private int comment_cnt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDto boardDto = (BoardDto) o;
        return bno == boardDto.bno && Objects.equals(writer, boardDto.writer) && Objects.equals(title, boardDto.title) && Objects.equals(content, boardDto.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bno, writer, title, content);
    }

    public BoardDto(){}

    public BoardDto(String writer, String title, String content) {
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
