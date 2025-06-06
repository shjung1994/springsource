package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>{
    
    // // WHERE b.WRITER = 'USER4'
    // // findByWriter
    // List<Board> findByWriter(String writer);
    // List<Board> findByTitle(String title);
    
    // // b.WRITER LIKE 'user4%'
    // List<Board> findByWriterStartingWith(String writer);
    // // b.WRITER LIKE '%user4'
    // List<Board> findByWriterEndingWith(String writer);
    // // b.WRITER LIKE '%user4%'
    // List<Board> findByWriterContaining(String writer);
    
    // // b.WRITER LIKE '%user4%' or b.content like '%내용%'
    // List<Board> findByWriterContainingOrContentContaining(String writer, String content);
    
    // // b.WRITER LIKE '%user4%' and b.content like '%내용%'
    // List<Board> findByWriterContainingAndContentContaining(String writer, String content);

    // // bno > 5 게시물 조회
    // List<Board> findByBnoGreaterThan(Long bno);

    // // bno > 0 order by bno desc
    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // // bno >= 5 and bno <= 10
    // // where bno between 5 and 10
    // List<Board> findByBnoBetween(Long start, Long end);

    // ------------------------------------------------------------------------------------
    // @Query
    // ------------------------------------------------------------------------------------

    // 여기서 Board는 entity 기준, Board.java에서 public class Board {} 이기에
    // @Query("select b from Board b where b.writer = ?1")
    @Query("select b from Board b where b.writer = :writer")
    // List<Board> findByWriter(String writer);
    List<Board> findByWriter(@Param("writer") String writer);
    
    @Query("select b from Board b where b.writer like ?1%")
    List<Board> findByWriterStartingWith(String writer);
    
    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining(String writer);

    // @Query("select b from Board b where b.bno > ?1 ")
    
    // b.title, b.writer 등 두개면 오브젝트 배열로 나옴
    @Query("select b.title, b.writer from Board b where b.title like %?1%")
    List<Object[]> findByTitle2(String title);

    // sql 구문 형식 사용
    // @Query(value = "select * from Board b where b.bno > ?1", nativeQuery = true)
    @NativeQuery("select * from Board b where b.bno > ?1")
    List<Board> findByBnoGreaterThan(Long bno);

}
