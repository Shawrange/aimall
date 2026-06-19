package com.aimall.controller;

import com.aimall.annotation.GlobalInterceptor;
import com.aimall.constants.Constants;
import com.aimall.entity.config.AppConfig;
import com.aimall.entity.enums.ResponseCodeEnum;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.exception.BusinessException;
import com.aimall.utils.FileUtils;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Validated
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController extends ABaseController {

    @Resource
    private AppConfig appConfig;

    @Resource
    private FileUtils fileUtils;

    @RequestMapping("/uploadImage")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO uploadCover(@NotNull MultipartFile file, Boolean createThumbnail) throws IOException {
        String filePath = fileUtils.uploadImage(file, createThumbnail);
        return getSuccessResponseVO(filePath);
    }

    @RequestMapping("/getResource")
    public void getResource(HttpServletResponse response, @NotEmpty String sourceName) {
        if (!StringTools.pathIsOk(sourceName)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String suffix = StringTools.getFileSuffix(sourceName);
        response.setContentType("image/" + suffix.replace(".", ""));
        response.setHeader("Cache-Control", "max-age=2592000");
        readFile(response, sourceName);
    }

    protected void readFile(HttpServletResponse response, String filePath) {
        try {
            File file = fileUtils.resolveFile(filePath);
            if (!file.exists() || !file.isFile()) {
                return;
            }
            try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file)) {
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
            }
        } catch (Exception e) {
            log.error("璇诲彇鏂囦欢寮傚父", e);
        }
    }
}

