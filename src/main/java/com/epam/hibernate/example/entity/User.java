package com.epam.hibernate.example.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Ivan_Lohvinau on 1/16/2017.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ")
    @Column(name = "U_ID")
    private Long id;

    @Column(name = "U_LOGIN")
    private String login;

    @Column(name = "U_PASSWORD")
    private String password;

    @OneToOne
    @JoinColumn(name = "U_ROLE")
    private Role role;

    public User() {
    }

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole(){
        return role;
    }
}
