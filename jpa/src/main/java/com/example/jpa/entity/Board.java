package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EntityListeners(value = AuditingEntityListener.class)
@Entity
public class Board {
// CREATE TABLE board(
// bno NUMBER(8) PRIMARY KEY,
// title varchar2(100) NOT NULL,
// content varchar2(2000) NOT NULL,
// writer varchar2(50) NOT NULL,
// regdate DATE DEFAULT sysdate
// );
// CREATE SEQUENCE board_seq;

    @Id
    @SequenceGenerator(name = "board_seq_gen", sequenceName = "board_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    private Long bno;

    // @Column(columnDefinition = "varchar2(100) NOT NULL")
    @Column(nullable = false, length = 100)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String content;
    
    @Column(nullable = false, length = 50)
    private String writer;

    @CreatedDate
    private LocalDateTime regDate;

}
