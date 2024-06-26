package com.example.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.file.entity.BoardEntity;
import com.example.file.entity.FileInfoEntity;
import com.example.file.repository.BoardRepository;

@Controller
public class DownloadController {
  // @GetMapping("/download")
  // public ResponseEntity<Resource> download() throws Exception {
  //   File file = new File("c:/js/images/evening.jpg");

  //   InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

  //   return ResponseEntity.ok()
  //       .header("content-disposition", "filename=" + URLEncoder.encode(file.getName(), "utf-8"))
  //       // .header("content-disposition", "filename=abcd.jpg")
  //       .contentLength(file.length()) // 다운로드 받는동안 얼만큼 진행됐는지 알 수 있음
  //       .contentType(MediaType.parseMediaType("application/octet-stream"))
  //       // octet-stream : 다운로드 되는 파일의 형태 (가장 많이 사용됨)
  //       // .contentType(MediaType.parseMediaType("image/jpeg")) // 화면에 이미지 출력
  //       // ===> content type, mime type
  //       .body(resource);
  // }

  @Autowired
  BoardRepository boardRepository;

  @GetMapping("/download")
  public ResponseEntity<Resource> download(
      @RequestParam int id) throws Exception {
    Optional<BoardEntity> opt = boardRepository.findById(id);
    BoardEntity board = opt.get();
    List<FileInfoEntity> files = board.getFiles();

    File file = new File("c:/java/" + files.get(0).getSavedFileName());

    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

    return ResponseEntity.ok()
        .header("content-disposition", "filename=" + URLEncoder.encode(file.getName(), "utf-8"))
        .contentLength(file.length())
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .body(resource);
  }
}
