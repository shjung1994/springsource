package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Long insert(BookDTO dto) {
        // DTO => entity

        // Book book = Book.builder().title(dto.getTitle())
        // .author(dto.getAuthor())
        // .price(dto.getPrice())
        // .build();
        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getCode();
    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> readAll() {
        List<Book> list = bookRepository.findAll();
        // entity => dto
        // 하나면
        // return modelMapper.map(book, BookDTO.class);
        // 여러개(리스트)면 람다식이용
        List<BookDTO> books = list.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return books;
    }

    public void modify(BookDTO dto) {
        Book book = bookRepository.findById(dto.getCode()).get();
        book.setPrice(dto.getPrice());
        bookRepository.save(book);
    }

    public void remove(Long code) {
        // repository 호출
        bookRepository.deleteById(code);

    }

}
