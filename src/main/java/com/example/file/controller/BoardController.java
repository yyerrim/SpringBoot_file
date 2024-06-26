package com.example.file.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.file.entity.BoardEntity;
import com.example.file.repository.BoardRepository;

@Controller
public class BoardController {
    @Autowired
    BoardRepository boardRepository;

    @GetMapping("/board")
    public String board(Model model) {
        List<BoardEntity> list = boardRepository.findAll();
        model.addAttribute("list", list);
        return "board";
    }

    @GetMapping("/board/{id}")
    public String boardDetail(
            Model model,
            @PathVariable("id") int id) {
        Optional<BoardEntity> opt = boardRepository.findById(id);
        model.addAttribute("board", opt.get());
        return "board_detail";
    }
}
