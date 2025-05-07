package com.example.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rest.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long>{

    // // where mno < 5
    // List<Board> findByMnoLessThan(Long mno);
    // // where mno < 10 order by mno desc
    // List<Board> findbyMnoLessThanOrderByMnoDesc(Long mno);
    // // where memoText like '%memo%'
    // List<Board> findByMemoTextContaining(String memoText);

}
