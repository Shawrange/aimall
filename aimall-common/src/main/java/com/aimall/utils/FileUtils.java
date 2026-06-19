package com.aimall.utils;

import com.aimall.constants.Constants;
import com.aimall.entity.config.AppConfig;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class FileUtils {

    private static final Set<String> ALLOWED_IMAGE_SUFFIXES = Set.of(".jpg", ".jpeg", ".png", ".gif", ".webp");

    @Resource
    private AppConfig appConfig;

    public String uploadImage(MultipartFile file, Boolean createThumbnail) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        String folderName = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern()) + "/";
        File folder = resolveFile(folderName);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new BusinessException("创建上传目录失败");
            }
        }
        String fileName = StringTools.getRandomString(Constants.LENGTH_30);
        String suffix = StringTools.getFileSuffix(file.getOriginalFilename()).toLowerCase(Locale.ROOT);
        if (!ALLOWED_IMAGE_SUFFIXES.contains(suffix)) {
            throw new BusinessException("不支持的图片格式");
        }
        String resultFileName = null;
        resultFileName = fileName + suffix;
        File imageFile = resolveFile(folderName + resultFileName);
        file.transferTo(imageFile);
        if (createThumbnail != null && createThumbnail) {
            String thumbnail = fileName + Constants.IMAGE_THUMBNAIL_SUFFIX + suffix;
            createImageThumbnail(imageFile.getAbsolutePath(), resolveFile(folderName + thumbnail).getAbsolutePath());
            return folderName + thumbnail;
        }
        return folderName + resultFileName;
    }

    public File resolveFile(String relativePath) throws IOException {
        if (!StringTools.pathIsOk(relativePath)) {
            throw new BusinessException("文件路径不合法");
        }
        File rootFolder = new File(appConfig.getProjectFolder(), Constants.FILE_FOLDER_FILE).getCanonicalFile();
        File targetFile = new File(rootFolder, relativePath).getCanonicalFile();
        if (!targetFile.toPath().startsWith(rootFolder.toPath())) {
            throw new BusinessException("文件路径不合法");
        }
        return targetFile;
    }

    private void createImageThumbnail(String filePath, String thumbnailPath) {
        final String CMD_CREATE_IMAGE_THUMBNAIL = "ffmpeg -i \"%s\" -vf scale=200:-1 \"%s\"";
        String cmd = String.format(CMD_CREATE_IMAGE_THUMBNAIL, filePath, thumbnailPath);
        ProcessUtils.executeCommand(cmd, true);
    }
}

