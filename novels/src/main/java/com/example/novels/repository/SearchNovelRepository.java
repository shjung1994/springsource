package com.example.novels.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNovelRepository {
    // 하나 조회
    Object[] getNovelById(Long id);

    // 전체 조회
    Page<Object[]> list(Pageable pageable);
}
