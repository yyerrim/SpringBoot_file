package com.example.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
      mFile.transferTo(new File("c:/java/" + oName)); // 파일 저장 핵심 명령어 : transferTo
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

  // 간단하게 할 때 사용 (파일 1개 업로드 할 때 가장 편함)
  @PostMapping("/upload2")
  @ResponseBody
  public String upload2(
      // @RequestParam("file") MultipartFile mFile) {

      // 파일 한개 더 업로드
      // @RequestParam("file") MultipartFile mFile,
      // @RequestParam("file2") MultipartFile mFile2) {

      // title 추가
      @RequestParam("file") MultipartFile mFile,
      @RequestParam("file2") MultipartFile mFile2,
      @RequestParam("title") String title) {

    String result = "";
    String oName = mFile.getOriginalFilename();
    // result += oName + "<br>" + mFile.getSize();
    // result += oName + "<br>" + mFile2.getOriginalFilename();
    result += oName + "<br>" + mFile2.getOriginalFilename() + "<br>" + title;
    // transferTo 사용하면 파일 저장 가능
    return result;
  }

  @GetMapping("/upload3")
  public String upload3() {
    return "upload3";
  }

  // RequestParam : 건바이건으로 동작 - a 오면 리퀘스트파람에 a
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
    Iterator<String> fileNames = mRequest.getFileNames();
    while (fileNames.hasNext()) {
      String fileName = fileNames.next();
      List<MultipartFile> mFiles = mRequest.getFiles(fileName);
      for (MultipartFile mFile : mFiles) {
        String oName = mFile.getOriginalFilename();
        long size = mFile.getSize();
        result += oName + " : " + size + "<br>";
      }
    }
    return result;
  }
}
