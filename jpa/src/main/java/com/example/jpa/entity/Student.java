package com.example.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Table(name = "studenttbl")
@Entity // == db table
public class Student {

    @Id
    // @GeneratedValue(strategy = GenerationType.~~)
    @SequenceGenerator(name="student_seq_gen",sequenceName = "student_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")

    private Long id; // id number(19,0) not null primary key (id), long으로 타입 만들면 19짜리 넘버로 만들어주고 

    private String name; // name varchar2(255 char)

}
