package com.example.movie.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;

@Data
public class PageResultDTO<E> {
    // 화면에 보여줄 데이터를 갖고있는 변수를 선언
    // (화면에 보여줄 목록)
    private List<E> dtoList;

    // 페이지번호 목록을 추출하기 위해 작성
    // 전체개수 / 페이지당 보여줄 개수 = 몇개의 페이지가 필요한가
    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    // 이전, 다음 보여주기 여부
    private boolean prev,next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResultDTO (List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
    
    this.dtoList = dtoList;
    this.pageRequestDTO = pageRequestDTO;
    this.totalCount = (int) totalCount;

    // 화면에 페이지 나누기 보여주기 위해 계산하는 부분
    int end = (int)(Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
    int start = end - 9;
    int last = (int) (Math.ceil(totalCount / (double) pageRequestDTO.getSize()));
    end = end > last ? last : end;

    // 1 보다 크면 prev, 다음페이지가 나올게 있으면 true로 설정
    this.prev = start > 1;
    this.next = totalCount > end * pageRequestDTO.getSize();

    // 숫자 만들어 주는 기능 1,2,3,4,5,6,7,8,9,10
    this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    if (prev) {
        this.prevPage = start-1;
    }
    if (next) {
        this.nextPage = end+1;
    }

    totalPage = this.pageNumList.size();
    // 사용자가 요청한 페이지
    this.current = pageRequestDTO.getPage();
    }
}
