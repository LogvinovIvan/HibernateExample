package com.epam.hibernate.example.entity;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "ATTACHED_TAGS")
public class Tag implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "TAG_SEQ")
    @Column(name = "T_ID")
    private Long id;

    @Column(name = "T_NAME")
    @NotEmpty
    @Size(min = 1, max = 45)
    private String name;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    @Override
    public String toString(){
        return String.valueOf(id);
    }

    @Override
    public Tag clone() throws CloneNotSupportedException {
        Object o = super.clone();
        Tag tag = (Tag) o;
        tag.id = id;
        tag.name = name;
        return tag;
    }
}
