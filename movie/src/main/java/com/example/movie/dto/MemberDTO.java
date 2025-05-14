package com.example.movie.dto;

import java.time.LocalDateTime;

import com.example.movie.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDTO {

    private Long mid;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private MemberRole memberRole;
}
