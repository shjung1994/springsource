package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

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
    
    @PostMapping("/create")
    public String postCreate(@ModelAttribute("book") @Valid BookDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("도서 작성 요청");

        if (result.hasErrors()) {
            return "/book/create";
        }
        // 서비스 호출
        Long code = bookService.insert(dto);

        // ?code=2030 => 화면단 $(param.code)
        // rttr.addAttribute(code, 2030);

        //session을 이용(주소줄에 따라가지 않음) => ${code}
        rttr.addFlashAttribute("code", code);
        return "redirect:/book/list";
    }

    // http://localhost:8080/book/list?page=1&size=10
    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) { // springframework Model model해주고 마지막 model.addAttribute해줌
        log.info("book list 요청 {}", pageRequestDTO);

        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        model.addAttribute("result", pageResultDTO); // list.html theach에 books 넣어줌
    }

    // http://localhost:8080/book/read?code=53
    // http://localhost:8080/book/modify?code=53

    @GetMapping({ "/read", "/modify" }) // 주소창에 read나 modify로 접근가능 LINE38,39
    public void getRead(Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get 요청 {}", code);

        BookDTO book = bookService.read(code);
        model.addAttribute("book", book);
    }

    @PostMapping("/modify")
    public String getModify(BookDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book modify 요청 {}", dto);

        // service호출
        bookService.modify(dto);
        // read로 가게끔
        rttr.addAttribute("code", dto.getCode());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/read";
    }

    // http://localhost:8080/book/remove?code=41
    // org.springframework.web.HttpRequestMethodNotSupportedException: Request
    // method 'GET' is not supported
    // get방식을 만들던, post에 get못쓰게하던

    @PostMapping("/remove")
    public String getRemove(Long code, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book remove 요청 {}", code);

        // 서비스 호출
        bookService.remove(code);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/list";
    }
}
