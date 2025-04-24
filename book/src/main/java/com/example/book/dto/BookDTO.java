package com.example.book.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookDTO {
    
    private Long code;

    private String title;

    private String author;
   
    private String price;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
