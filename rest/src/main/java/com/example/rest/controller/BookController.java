package com.example.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.service.BookService;

import jakarta.validation.Valid;

// 일반 컨트롤러 + REST
// 데이터만 내보내기: ResponseEntity or @ResponseBody

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService; // final을 부르니 @RequiredArgsConstructor를 호출해야 함

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("book") BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("도서 작성 폼 요청");
    }
    
    @ResponseBody
    @PostMapping("/create")
    public Long postCreate(@RequestBody BookDTO dto) {
        log.info("도서 작성 요청");
        // 서비스 호출
        Long code = bookService.insert(dto);

        return code;
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO>> getList(PageRequestDTO pageRequestDTO, Model model) { // springframework Model model해주고 마지막 model.addAttribute해줌
        log.info("book list 요청 {}", pageRequestDTO);

        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    // http://localhost:8080/book/read?code=20
    // @ResponseBody
    // @GetMapping({ "/read", "/modify" }) // 주소창에 read나 modify로 접근가능 LINE38,39
    // public BookDTO getRead(Long code, PageRequestDTO pageRequestDTO, Model model) {
    //     log.info("book get 요청 {}", code);

    //     BookDTO book = bookService.read(code);
    //     return book;
    // }

    // http://localhost:8080/book/read/20
    @ResponseBody
    @GetMapping({ "/read/{code}", "/modify" }) // 주소창에 read나 modify로 접근가능 LINE38,39
    public BookDTO getRead(@PathVariable Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get 요청 {}", code);

        BookDTO book = bookService.read(code);
        return book;
    }

    @ResponseBody
    @PutMapping("/modify")
    public Long getModify(@RequestBody BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("book modify 요청 {}", dto);

        // service호출
        bookService.modify(dto);

        return dto.getCode();
    }

    @DeleteMapping("/remove/{code}")
    public ResponseEntity<Long> getRemove(@PathVariable Long code, PageRequestDTO pageRequestDTO) {
        log.info("book remove 요청 {}", code);

        // 서비스 호출
        bookService.remove(code);

        return new ResponseEntity<Long>(code, HttpStatus.OK);
    }
}
