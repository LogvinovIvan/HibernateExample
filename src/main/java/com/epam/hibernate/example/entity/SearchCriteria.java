package com.epam.hibernate.example.entity;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 1/16/2017.
 */
public class SearchCriteria {
    private Set<Author> authors = new HashSet<>();
    private Set<Tag> tags = new HashSet<>();

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors.addAll(authors);
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags.addAll(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCriteria that = (SearchCriteria) o;
        return Objects.equals(authors, that.authors) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authors, tags);
    }
}
