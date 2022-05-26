package com.example.streamarr.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "chat_moderator")
public class ChatModerator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_moderator_id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="chat_id", nullable=false)
    private Chat chat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account moderator;
}