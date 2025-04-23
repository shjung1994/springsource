package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.ToDoDto;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public List<ToDoDto> list(boolean completed) {

        List<ToDo> list = todoRepository.findByCompleted(completed);

        // ToDo entity => ToDoDTO로 변경 후 리턴하는 작업해야 함
        // List<ToDoDto> todos = new ArrayList<>();
        
        // 리스트에서 하나씩 꺼내서 foreach돌리기
        // list.forEach(todo -> {
        //     ToDoDto dto = modelMapper.map(todo, ToDoDto.class);
        //     todos.add(dto);
        // });
        // 람다 사용해서 더 간단히
        List<ToDoDto> todos = list.stream().map(todo -> modelMapper.map(todo, ToDoDto.class)).collect(Collectors.toList());
        return todos;
    }
    
}
