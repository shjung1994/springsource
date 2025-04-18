package com.example.jpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoService {
    // Repository 메소드 호출한 후 결과 받기

    private final MemoRepository memoRepository;

    public List<MemoDTO> getList(){
        List<Memo> list = memoRepository.findAll();
        
        // Memo => MemoDTO 옮기기
        // List<MemoDTO> memos = new ArrayList<>();
        // for (Memo memo : list) {
        //     MemoDTO dto = MemoDTO.builder()
        //     .mno(memo.getMno())
        //     .memoText(memo.getMemoText())
        //     .build();
        //     memos.add(dto);
        // }

        // 또는

        //list.stream().forEach(memo -> System.out.println(memo));
        // 또는
        // List<MemoDTO> memos = list.stream().map(memo -> {
        //     MemoDTO dto = MemoDTO.builder()
        //     .mno(memo.getMno())
        //     .memoText(memo.getMemoText())
        //     .build();
        //     return dto;
        // }).collect(Collectors.toList());

        // 또는 메소드를 만들고
        List<MemoDTO> memos = list.stream()
            .map(memo -> entityToDto(memo))
            .collect(Collectors.toList());

        return memos;

    }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        // entity => dto
        MemoDTO dto = entityToDto(memo);
        return dto;
    }

    // Id는 실제 id라는 변수를 얘기 x, Memo.java등에 있는 @Id라는 annotation이 붙은 변수를 말함
    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());
        // update 실행 => 수정된 Memo return
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할 memo는 MemoDTO에 저장
        // MemoDTO = Memo 변환
        Memo memo = dtoToEntity(dto);
        // 새로 저장한 memo 리턴됨
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private Memo dtoToEntity(MemoDTO memoDTO){
        Memo memo = Memo.builder()
        .mno(memoDTO.getMno())
        .memoText(memoDTO.getMemoText())
        .build();
        return memo;
    }

    // 메소드
    private MemoDTO entityToDto(Memo memo){
        MemoDTO dto = MemoDTO.builder()
        .mno(memo.getMno())
        .memoText(memo.getMemoText())
        .createdDate(memo.getCreatedDate())
        .updatedDate(memo.getUpdatedDate())
        .build();
        return dto;
        // 또는
        // MemoDTO dto = new MemoDTO();
        // dto.setMno(memo.getMno());
        // dto.setMemoText(memo.getMemoText());
        // .createdDate
        // return dto;
    }

}
