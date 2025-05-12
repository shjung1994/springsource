package com.example.board.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    
    // 로그인처리 해주는 method
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username {}", username);

        Member Member = memberRepository.findByEmailAndFromSocial(username, false);

        if (Member == null) throw new UsernameNotFoundException("이메일 확인");

        // entity => dto
        OAuthMemberDTO clubAuthMemberDTO = new OAuthMemberDTO(Member.getEmail(),
        Member.getPassword(), Member.isFromSocial(),
        Member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+ role.name()))
        .collect(Collectors.toList()));

        clubAuthMemberDTO.setName(Member.getName());
        clubAuthMemberDTO.setFromSocial(Member.isFromSocial());

        return clubAuthMemberDTO;
    }
    
}
