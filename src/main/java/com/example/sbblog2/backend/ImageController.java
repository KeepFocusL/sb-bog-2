package com.example.sbblog2.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/backend/images/")
public class ImageController {
    @Value("${custom.upload.base-path}")
    String uploadBasePath;
    @Value("${custom.upload.from-md-path}")
    String fromMdPath;

    @PostMapping("uploadFromMd")
    public Map<String, Object> uploadFromVditor(@RequestParam("image") MultipartFile image) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", 1);

        if (image != null && !image.isEmpty()) {
            File dir = new File(uploadBasePath + File.separator + fromMdPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = image.getOriginalFilename();
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID() + suffix;
            try {
                image.transferTo(new File(dir.getAbsolutePath() + File.separator + newFilename));
            } catch (IOException e) {
                result.put("success", 0);
                result.put("message", "上传失败：" + e.getMessage());
            }

            Map<String, String> file = new HashMap<>();
            file.put("url", "/" + fromMdPath + "/" + newFilename);
            result.put("file", file);
        }

        return result;
    }
}
