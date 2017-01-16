package com.epam.hibernate.example.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 1/16/2017.
 */
@Entity
@Table(name = "NEWS")
public class FullNews {
    @Embedded
    private News news;

    @ManyToMany
    @JoinTable(name = "NEWS_HAS_AUTHORS",
            joinColumns = @JoinColumn(name = "NEWS_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORS_ID"))
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(name = "NEWS_HAS_TAGS",
            joinColumns = @JoinColumn(name = "NEWS_ID"),
            inverseJoinColumns = @JoinColumn(name = "ATTACHED_TAGS_ID"))
    private Set<Tag> tags;

    @OneToMany(targetEntity = Comment.class, mappedBy = "news")
    private List<Comment> comments;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullNews fullNews = (FullNews) o;
        return Objects.equals(news, fullNews.news) &&
                Objects.equals(authors, fullNews.authors) &&
                Objects.equals(tags, fullNews.tags) &&
                Objects.equals(comments, fullNews.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(news, authors, tags, comments);
    }
}
