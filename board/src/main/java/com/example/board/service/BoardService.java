package com.example.board.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {
    
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    // 새글 작성
    public Long create(BoardDTO dto) {
        // dto => entity 변경
        Board board = dtoToEntity(dto);
        // 저장
        Board newBoard = boardRepository.save(board);
        return newBoard.getBno();
    }

    // 삭제
    @Transactional // Reply, BoardTBL 두개의 테이블 접근 => 한꺼번에 처리,,, 하나라도 에러나면 롤백시켜주세요
    public void delete(Long bno) {
        // 연관관계 데이터 정리 => 댓글
        // SQL: 댓글 선 삭제 후 게시글 삭제 OR 댓글의 부모를 null로 변경 후 삭제

        // 댓글은 bno로 찾아야 함
        // 댓글 삭제: 1) bno로 댓글 찾기 2) 삭제

        // 댓글삭제
        replyRepository.deleteByBoardBno(bno);
        // 본문삭제
        boardRepository.deleteById(bno);
    }

    // 수정
    public Long update(BoardDTO dto) {
        // 수정할 대상 찾기(id로 찾기)
        Board board = boardRepository.findById(dto.getBno()).orElseThrow();
        // 내용 업데이트
        board.changeContent(dto.getContent());
        board.changeTitle(dto.getTitle());
        // 저장
        boardRepository.save(board);
        
        return board.getBno();
    }

    public BoardDTO getRow(Long bno) {
        Object[] entity = boardRepository.getBoardByBno(bno);

        // [Board(bno=3, title=Board Title3, content=Board Content3), Member(email=user5@gmail.com, password=1111, name=USER5), 3]
        return entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]);
    }

    public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageable);
        // Function<T,R>: T를 R로 변환해서(매핑해서) 처리해 줘!
        Function<Object[], BoardDTO> fn = (entity -> entityToDto((Board)entity[0], (Member)entity[1], (Long)entity[2]));

        List<BoardDTO> dtoList =  result.stream().map(fn).collect(Collectors.toList());
        Long totalCount = result.getTotalElements();

        PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
        .dtoList(dtoList)
        .totalCount(totalCount)
        .pageRequestDTO(pageRequestDTO)
        .build();

        return pageResultDTO;
    }

    private BoardDTO entityToDto(Board board, Member member, Long replyCount) {
        BoardDTO dto = BoardDTO.builder()
        .bno(board.getBno())
        .title(board.getTitle())
        .content(board.getContent())
        .createdDate(board.getCreatedDate())
        .email(member.getEmail())
        .name(member.getName())
        .replyCount(replyCount)
        .build();
        return dto;
    }

    private Board dtoToEntity(BoardDTO dto) {
        Board board = Board.builder()
        .bno(dto.getBno())
        .title(dto.getTitle())
        .content(dto.getContent())
        .member(Member.builder().email(dto.getEmail()).build())
        .build();
        return board;
    }

}
