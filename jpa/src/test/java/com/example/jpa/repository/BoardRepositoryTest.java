package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void queryMethodTest() {

        // System.out.println(boardRepository.findByWriter("user4"));
        // System.out.println(boardRepository.findByTitle("board Title1"));
        // System.out.println(boardRepository.findByWriterStartingWith("user")); //user%
        // System.out.println(boardRepository.findByWriterEndingWith("user")); // %user
        // System.out.println(boardRepository.findByWriterContaining("user")); // %user%
        
        // System.out.println(boardRepository.findByWriterContainingOrContentContaining("5", "9"));
        // System.out.println(boardRepository.findByWriterContainingAndContentContaining("5", "9"));

        // System.out.println(boardRepository.findByBnoGreaterThan(5L));
        // System.out.println(boardRepository.findByBnoGreaterThanOrderByBnoDesc(0L));
        // System.out.println(boardRepository.findByBnoBetween(5L, 10L));

    }

    // crud 만들기
    @Test
    public void insertTest(){
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder().title("test"+i).content("test"+i).writer("test"+i).build();
            boardRepository.save(board);
        });
    }
    
    @Test
    public void updateTest(){
        Board board = boardRepository.findById(2L).get();
        board.setTitle("title update");
        boardRepository.save(board);
    }

    @Test
    public void readTest(){
        Board board = boardRepository.findById(2L).get();
        System.out.println(board);
    }

    @Test
    public void listTest(){
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void deleteTest(){
        boardRepository.deleteById(10L);
    }
}
