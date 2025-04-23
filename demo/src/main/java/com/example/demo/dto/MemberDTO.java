package com.example.demo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDTO {

    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)[A-Za-z.+\\d]{6,12}$",message = "아이디는 영대소문자,특수문자,숫자를 포함해서 6~12자리입니다")
    @NotBlank(message = "아이디를 확인해 주세요")
    private String userid;

    @NotBlank(message = "패스워드를 확인해 주세요")
    private String password;
    
    @NotBlank(message = "이메일을 확인해 주세요")
    @Email(message = "이메일 형식을 확인해 주세요")
    private String email;

    // @Length(min = 2, max=5)
    @Pattern(regexp = "^[가-힣]{2,5}$",message = "나이는 2~5 자리로 입력해야 합니다")
    private String name;

    @NotNull(message = "나이는 필수요소입니다")
    @Min(value = 0, message = "나이는 최소 0 이상이어야 합니다")
    @Max(value = 140, message = "나이는 최대 140 이하여야 합니다")
    private Integer age;

    private boolean check;
}
