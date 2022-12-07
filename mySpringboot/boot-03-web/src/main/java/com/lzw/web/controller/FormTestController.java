package com.lzw.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 测试文件上传
 *
 * @author : lzw
 * @date : 2022/12/6
 * @since : 1.0
 */
@Slf4j
@Controller
public class FormTestController {

    @GetMapping("/form_layouts")
    public String formlayouts() {

        return "form/form_layouts";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestPart("headerImg") MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {

        log.info("上传的信息：email={},username={},headerImg={},photos={}",
                email, username, headerImg.getSize(), photos.length);
        if (!headerImg.isEmpty()) {
            // 开始处理文件
            String originalFilename = headerImg.getOriginalFilename();
            // 将文件存储到本地路径下
            headerImg.transferTo(new File("D:\\temp\\" + originalFilename));
        }

        if (photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String originalFilename = photo.getOriginalFilename();
                    photo.transferTo(new File("D:\\temp\\" + originalFilename));
                }
            }
        }

        return "main";
    }
}
