package com.example.movie.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class OAuthMemberDTO extends User{

    private MemberDTO memberDTO;

    // username: id 개념
    public OAuthMemberDTO(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public OAuthMemberDTO(MemberDTO memberDTO) {
        super(memberDTO.getEmail(), memberDTO.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+memberDTO.getMemberRole())));
        this.memberDTO = memberDTO;
    }
    
}
