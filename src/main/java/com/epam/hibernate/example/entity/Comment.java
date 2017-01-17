package com.epam.hibernate.example.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;


@Entity
@Table(name = "COMMENT_NEWS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "COMMENT_SEQ")
    @Column(name = "C_ID")
    private Long id;

    @NotEmpty
    @Column(name = "C_CONTENT")
    private String text;

    @NotEmpty
    @Column(name = "C_DATE")
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID_USER")
    private User user;

    @ManyToOne
    @JoinColumn(name = "C_NEWS")
    private FullNews news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FullNews getNews() {
        return news;
    }

    public void setIdNews(FullNews news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(date, comment.date) &&
                Objects.equals(user, comment.user) &&
                Objects.equals(news, comment.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date, user, news);
    }
}
