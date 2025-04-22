package com.example.relation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        // 팀(부모) 정보 삽입
        Team team = teamRepository.save(Team.builder().teamName("team2").build());
    
        // 회원(자식) 정보 삽입
        // teamMemberRepository.save(TeamMember.builder().userName("user1").team(team).build());
    }

    // 있는 팀에 집어넣어보자!
    @Test
    public void insertTest2() {
        // 팀 정보 삽입
        Team team = teamRepository.findById(1L).get();
    
        // 회원 정보 삽입
        teamMemberRepository.save(TeamMember.builder().userName("user2").team(team).build());
    }

    @Test
    public void readTest1(){
        // 팀 조회
        Team team = teamRepository.findById(1L).get();
        // 멤버 조회
        TeamMember teamMember = teamMemberRepository.findById(11L).get();
        System.out.println(team);
        System.out.println(teamMember);
    }

    @Test
    public void readTest2(){
        // 멤버의 팀정보
        TeamMember teamMember = teamMemberRepository.findById(11L).get();
        System.out.println(teamMember);
        // 객체그래프탐색
        System.out.println(teamMember.getTeam());
    }

    @Test
    public void updateTest(){
        // 1번 팀원의 팀 변경: 2번 팀으로 변경
        TeamMember member = teamMemberRepository.findById(11L).get();
        Team team = teamRepository.findById(2L).get();
        member.setTeam(team);

        teamMemberRepository.save(member); // 여기서의 save = insert, update 중 update 개념
    }

    @Test
    public void deleteTest(){
        // 1번 팀 삭제
        // 무결성 제약조건이 위배되었습니다. 자식 레코드가 발견 되었습니다.
        // teamRepository.deleteById(11L);

        // 해결
        // 1. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 이동
        // 2. 자식 삭제 후 부모 삭제
        
        // 1. 이용
        // 1번 팀원의 팀을 2번 팀으로 이동, 여기서 주인이 누구인지 알려줘야 함, 주인관계지정 필수(N쪽이 주인)
        TeamMember member = teamMemberRepository.findById(11L).get();
        Team team = teamRepository.findById(2L).get();
        member.setTeam(team);
        teamMemberRepository.save(member);

        // 팀 삭제
        teamRepository.deleteById(1L);
    }

    // ---------------------------
    // 양뱡향 연관관계: @OneToMany
    // -> 단방향 2개
    // ---------------------------

    // 팀에서 회원정보 접근
    // @Transactional
    @Test
    public void readBiTest1(){
        // 팀 찾기
        Team team = teamRepository.findById(2L).get();
        System.out.println(team);
        // 객체그래프탐색
        team.getMembers().forEach(member -> System.out.println(member));
    }


    // ---------------------------
    // 양뱡향: Cascade
    // 영속성 전이: Cascade
    // ---------------------------

    @Test
    public void insertTest3() {
        Team team = Team.builder().teamName("team3").build();
        
        TeamMember member = TeamMember.builder().userName("홍길동").team(team).build();
        // teamMemberRepository.save를 안하고 부모에게 자식에게 추가만 해놓고 부모만 저장했더니 멤버도 같이 들어감
        team.getMembers().add(member); // 팀에다 멤버를 추가한다
        teamRepository.save(team); // 그리고 저장한다
    }

    @Test
    public void deleteTest2(){
        // 부모 삭제 시 자식도 같이 삭제
        // deleteTest() 와 비교
        teamRepository.deleteById(8L);
    }

    // 결론! 부모 엔티티를 통해서 자식 엔티티를 관리할 수 있더라(상속으로)

}
