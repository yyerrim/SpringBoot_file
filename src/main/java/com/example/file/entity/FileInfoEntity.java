package com.example.file.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "file_info")
@Data
public class FileInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String fileName;

    // 파일명 같은 경우 파일이 바껴서 저장되는 문제
    String savedFileName;

    @ManyToOne
    BoardEntity board;
}
