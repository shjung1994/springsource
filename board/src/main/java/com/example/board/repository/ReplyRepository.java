package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // bno를 기준으로 삭제
    // ex) DELETE FROM reply WHERE board_bno = 1;

    @Modifying // 아래 문들을 적용시켜주는 것... (delete, update시 무조건 붙여야 함)
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBoardBno(Long bno);

    // 특정 글 조회시 달려있는 댓글 모두 가져오기
    List<Reply> findByBoardOrderByRno(Board board);

}
