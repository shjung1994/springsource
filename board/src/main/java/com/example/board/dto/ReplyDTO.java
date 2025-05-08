package com.example.board.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyer;

    // 게시글 번호(부모 번호)
    private Long bno;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
    
}
