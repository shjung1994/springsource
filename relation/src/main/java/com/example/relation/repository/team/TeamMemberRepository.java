package com.example.relation.repository.team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    // team을 기준으로 멤버 찾기
    List<TeamMember> findByTeam(Team team);
    
    // team을 기준으로 멤버 찾기 => 멤버, 팀정보 조회
    @Query("SELECT m,t FROM TeamMember m JOIN m.team t WHERE t.id = :id")
    List<Object[]> findByMemberEqualTeam(Long id);
    
}
