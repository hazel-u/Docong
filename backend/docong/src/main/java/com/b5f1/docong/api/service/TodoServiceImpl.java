package com.b5f1.docong.api.service;

import com.b5f1.docong.api.dto.request.ModifyTodoStatusReqDto;
import com.b5f1.docong.api.dto.request.SaveTodoReqDto;
import com.b5f1.docong.core.domain.todo.Todo;
import com.b5f1.docong.core.domain.todo.UserTodo;
import com.b5f1.docong.core.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;

    @Override
    public Long saveTodo(SaveTodoReqDto reqDto) {
        Todo todo = reqDto.toEntity();
        UserTodo userTodo = UserTodo.builder().build();
        todo.addUserTodo(userTodo);
        return todoRepository.save(todo).getSeq();
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new IllegalStateException("없는 Todo 입니다."));
        todoRepository.delete(todo);
    }

    @Override
    public void modifyTodo(Long id, SaveTodoReqDto reqDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new IllegalStateException("없는 Todo 입니다."));
        todo.changeTodo(reqDto.getTitle(), reqDto.getContent(), reqDto.getWorkImportance(),reqDto.getWorkProficiency(),reqDto.getWorkType());
    }

    @Override
    public void modifyStatus(Long id, ModifyTodoStatusReqDto reqDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new IllegalStateException("없는 Todo 입니다."));
        todo.changeStatus(reqDto.getTodoStatus());
    }
}
