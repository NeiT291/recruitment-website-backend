package org.neit.backend.utils;

import org.neit.backend.exception.AppException;
import org.neit.backend.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUtils {
    public void uploadFile(MultipartFile file, String filePath) throws IOException {
        File fileFolder = new File(filePath);
        if (!fileFolder.exists()){
            fileFolder.mkdirs();
        }
        File uploadFile = new File(filePath + file.getOriginalFilename());
        file.transferTo(uploadFile);
        if (!uploadFile.exists() || uploadFile.isDirectory()){
            throw new AppException(ErrorCode.CANNOT_UPLOAD_IMAGE);
        }
    }
}
