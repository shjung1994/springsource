package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {
    @Autowired
    private MemoRepository memoRepository;
    
    // test 메소드 작성
    @Test
    public void insertTest(){
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Memo memo = Memo.builder().memoText("memoText"+i).build();
            memoRepository.save(memo);
        });
    }
    
    @Test
    public void updateTest(){
        // Memo memo = Memo.builder().mno(1L).memoText("memoText update").build();
        Memo memo = memoRepository.findById(1L).get();
        // memo.setMemoText("memoText update");
        memo.changeMemoText("memoText update"); // setter 안쓰면 memo.java에 change로 썻으니
        memoRepository.save(memo);
    }

    @Test
    public void readTest(){
        Memo memo = memoRepository.findById(1L).get();
        System.out.println(memo);
    }

    @Test
    public void listTest(){
        // memoRepository.findAll(): entity모양으로 담긴 list를 돌려준다는 소리
        memoRepository.findAll().forEach(memo -> System.out.println(memo));
    }

    @Test
    public void deleteTest(){
        memoRepository.deleteById(10L);
    }

}
