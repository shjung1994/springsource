package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;

    // 댓글 삽입
    public Long create(ReplyDTO dto) {
        // dto to entity
        Reply reply = dtoToEntity(dto);
        // 삽입 후 rno 리턴
        return replyRepository.save(reply).getRno();

    }
    
    public List<ReplyDTO> getList(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> result = replyRepository.findByBoardOrderByRno(board);
        
        // Reply => ReplyDTO로 변경
        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    // rno 이용해서 댓글하나 가져오기
    public ReplyDTO get(Long rno) {
        Reply reply = replyRepository.findById(rno).get();
        return entityToDto(reply);
    }

    // 댓글 수정하기
    public Long update(ReplyDTO dto) {
        // 수정 대상 찾기
        Reply reply = replyRepository.findById(dto.getRno()).get();

        // 변경부분 적용
        reply.changeText(dto.getText());
        
        // 저장
        return replyRepository.save(reply).getRno();
    }

    // 댓글 삭제하기
    public void delete(Long rno) {
        replyRepository.deleteById(rno);
    }

    private ReplyDTO entityToDto(Reply reply) {
    ReplyDTO dto = ReplyDTO.builder()
    .rno(reply.getRno())
    .text(reply.getText())
    .replyerEmail(reply.getReplyer().getEmail())
    .replyerName(reply.getReplyer().getName())
    .bno(reply.getBoard().getBno())
    .createdDate(reply.getCreatedDate())
    .build();
    return dto;
}

private Reply dtoToEntity(ReplyDTO dto) {
    Reply reply = Reply.builder()
    .rno(dto.getRno())
    .text(dto.getText())
    .replyer(Member.builder().email(dto.getReplyerEmail()).build())
    .board(Board.builder().bno(dto.getBno()).build())
    .build();
    return reply;
}

}
