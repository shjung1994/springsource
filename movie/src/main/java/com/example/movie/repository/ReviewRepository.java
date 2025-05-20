package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    @Modifying // delete, update시 반드시 작성
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);

    // 멤버 탈퇴 시 해당 멤버가 작성한 모든 리뷰 제거
    @Modifying // delete, update시 반드시 작성
    @Query("DELETE FROM Review r WHERE r.member = :member")
    void deleteByMember(Member member);

    // movie mno를 이용해 리뷰 가져오기
    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH) // 이거할때 member는 같이 가져와줘 라는 뜻
    List<Review> findByMovie(Movie movie);



}
