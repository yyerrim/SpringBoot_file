package com.example.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.file.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

}
