package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // bno를 기준으로 삭제
    // ex) DELETE FROM reply WHERE board_bno = 1;

    @Modifying // 아래 문들을 적용시켜주는 것... (delete, update시 무조건 붙여야 함)
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBoardBno(Long bno);

}
