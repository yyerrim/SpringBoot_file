package com.example.file.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileInfo {
  MultipartFile file;

  // upload5
  MultipartFile[] files;
}
