package com.example.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.file.entity.BoardEntity;
import com.example.file.entity.FileInfoEntity;
import com.example.file.repository.BoardRepository;
import com.example.file.repository.FileInfoRepository;

@Controller
public class UploadController {
  // 파일 업로드는 무조건 post 방식이어야함 (다른 방식은 지원 안함)

  @GetMapping("/upload1")
  public String upload1() {
    return "upload1";
  }

  // 가장 전통적인 방법 (구닥다리지만 가장 많은 일을 할 수 있음)
  @PostMapping("/upload1")
  @ResponseBody
  public String upload1Post(MultipartHttpServletRequest mRequest) {
    String result = "";
    // 사용자가 첨부시킨 파일의 내용물이 MultipartFile 형태로 나옴
    MultipartFile mFile = mRequest.getFile("file"); // 파일 내놔
    String oName = mFile.getOriginalFilename(); // 첨부시킨 당시의 파일 이름
    result += oName + "<br>" + mFile.getSize();
    // getSize() : 사이즈 제한을 두고 싶을때 사용

    // 파일 저장
    // mFile.transferTo(new File("c:/java/" + oName));
    // // check 예외이기 때문에 try-catch로 묶어줘야함
    try {
      mFile.transferTo(new File("c:/java/" + oName)); // 파일 저장 핵심 명령어 : transferTo (NAS - 서버에 저장)
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @GetMapping("/upload2")
  public String upload2() {
    return "upload2";
  }

  // // 간단하게 할 때 사용 (파일 1개 업로드 할 때 가장 편함)
  // @PostMapping("/upload2")
  // @ResponseBody
  // public String upload2(
  //     // @RequestParam("file") MultipartFile mFile) {

  //     // 파일2 업로드 추가
  //     // @RequestParam("file") MultipartFile mFile,
  //     // @RequestParam("file2") MultipartFile mFile2) {

  //     // title 추가
  //     @RequestParam("file") MultipartFile mFile,
  //     @RequestParam("file2") MultipartFile mFile2,
  //     @RequestParam("title") String title) {

  //   String result = "";
  //   String oName = mFile.getOriginalFilename();
  //   // result += oName + "<br>" + mFile.getSize();
  //   // result += oName + "<br>" + mFile2.getOriginalFilename();
  //   result += oName + "<br>" + mFile2.getOriginalFilename() + "<br>" + title;
  //   // transferTo 사용하면 파일 저장 가능
  //   return result;
  // }

  @GetMapping("/upload3")
  public String upload3() {
    return "upload3";
  }

  // RequestParam : 건바이건으로 동작 - a 오면 RequestParam에 a
  // ModelAttribute : 여러건을 한꺼번에 동작
  //                 DTO 파일 안에 변수가 어떻게 작성되어있느냐에 따라서 들어가는 데이터도 여러건이 될 수 있음
  @PostMapping("/upload3")
  @ResponseBody
  public String upload3Post(@ModelAttribute com.example.file.model.FileInfo info) {
    String result = "";
    String oName = info.getFile().getOriginalFilename();
    result += oName + "<br>" + info.getFile().getSize();
    return result;
  }

  @GetMapping("/upload4")
  public String upload4() {
    return "upload4";
  }

  @PostMapping("/upload4")
  @ResponseBody
  public String upload4Post(
      // 여러개 올리고싶을때 RequestParam에서는 배열 사용
      @RequestParam("file") MultipartFile[] mFiles) {
    String result = "";
    for (MultipartFile mFile : mFiles) {
      String oName = mFile.getOriginalFilename();
      result += oName + " : " + mFile.getSize() + "<br>";
    }
    return result;
  }

  @GetMapping("/upload5")
  public String upload5() {
    return "upload5";
  }

  @PostMapping("/upload5")
  @ResponseBody
  public String upload5Post(@ModelAttribute com.example.file.model.FileInfo info) {
    String result = "";
    for (MultipartFile mFile : info.getFiles()) {
      String oName = mFile.getOriginalFilename();
      result += oName + " : " + mFile.getSize() + "<br>";
    }
    return result;
  }

  @GetMapping("/upload6")
  public String upload6() {
    return "upload6";
  }

  @PostMapping("/upload6")
  @ResponseBody
  public String upload6Post(MultipartHttpServletRequest mRequest) {
    String result = "";
    // input의 이름을 모르기 때문에
    Iterator<String> fileNames = mRequest.getFileNames();
    // 반복횟수 모름 -> while문 사용
    while (fileNames.hasNext()) {
      String fileName = fileNames.next(); // 사용자가 업로드 할 당시의 name이 전달됨
      List<MultipartFile> mFiles = mRequest.getFiles(fileName); // 진짜 MultipartFile 나옴
      for (MultipartFile mFile : mFiles) {
        String oName = mFile.getOriginalFilename();
        long size = mFile.getSize();
        result += oName + " : " + size + "<br>";
      }
    }
    return result;
  }

  // 데이터베이스에 저장하기
  @Autowired
  BoardRepository boardRepository;
  @Autowired
  FileInfoRepository fileInfoRepository;

  @Transactional // 데이터 저장하다가 에러 발생하면 롤백 - RuntimeException에만 동작
  @PostMapping("/upload2")
  @ResponseBody
  public String upload2(
      @RequestParam("title") String title,
      @RequestParam("file") MultipartFile mFile,
      @RequestParam("file2") MultipartFile mFile2) {
    String result = "";
    String oName = mFile.getOriginalFilename();
    result += title + "<br>" + oName + "<br>" + mFile2.getOriginalFilename();

    // save 하려면 entity로 new 되어있어야함
    BoardEntity board = new BoardEntity();
    board.setTitle(title);
    BoardEntity savedBoard = boardRepository.save(board);

    // 파일명 같은 경우 파일이 바껴서 저장되는 문제
    File f = new File("c:/java/" + oName);
    String sName = oName;
    if (f.exists()) { // = f.isFile() : 같은 파일명이 존재함
      // 파일명 현재시각 .sql
      int idx = oName.indexOf(".");
      String name = oName.substring(0, idx); // 파일명 ex)location_data
      String ext = oName.substring(idx); // .sql
      sName = name + System.currentTimeMillis() + ext;
    }

    FileInfoEntity fileInfo = new FileInfoEntity();
    fileInfo.setBoard(savedBoard);
    fileInfo.setFileName(oName);
    fileInfo.setSavedFileName(sName);
    fileInfoRepository.save(fileInfo);

    // 파일 저장
    try {
      mFile.transferTo(new File("c:/java/" + sName));
    } catch (IllegalStateException e) {
      // 오류 발생시 RuntimeException으로 던지게 바꾸기 -> 롤백 가능할 수 있도록 하기 위해
      throw new RuntimeException(e.getMessage());
      // e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
      // e.printStackTrace();
    }

    return result;
  }
}
