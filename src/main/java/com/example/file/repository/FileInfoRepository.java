package com.example.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.file.entity.FileInfoEntity;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, Integer> {

}
