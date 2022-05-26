package com.example.streamarr.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "account")
@NamedQueries({
        @NamedQuery(name = "Account.getAccountByToken", query = "select a from Account a where a.token = :token"),
        @NamedQuery(name = "Account.getAccountByUsername", query = "select a from Account a where a.username = :username")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "color")
    private String color;

    @Column(name = "token")
    private String token;

    @OneToOne(mappedBy = "owner")
    private Chat chat;
}