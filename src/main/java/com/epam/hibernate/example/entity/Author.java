package com.epam.hibernate.example.entity;

import com.sun.istack.internal.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Table(name = "AUTHORS")
public class Author implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @SequenceGenerator(name = "author_seq", sequenceName = "AUTHORS_SEQ")
    @Column(name = "A_ID")
    private Long id;

    @Column(name = "A_NAME")
    @NotNull
    @Size(min = 1, max = 45, message = "Wrong name")
    private String name;

    @Column(name = "A_SURNAME")
    @NotEmpty
    @Size(min = 1, max = 45)
    private String surname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name) &&
                Objects.equals(surname, author.surname);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    @Override
    public Author clone() throws CloneNotSupportedException {
        Author author = (Author) super.clone();
        author.id = id;
        author.name = name;
        author.surname = surname;
        return author;
    }
}
