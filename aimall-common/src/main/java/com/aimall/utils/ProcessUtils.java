package com.aimall.utils;

import com.aimall.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessUtils {
    private static final Logger logger = LoggerFactory.getLogger(ProcessUtils.class);

    private static final String osName = System.getProperty("os.name").toLowerCase();

    public static String executeCommand(String cmd, Boolean showLog) throws BusinessException {
        if (StringTools.isEmpty(cmd)) {
            return null;
        }

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            //鍒ゆ柇鎿嶄綔绯荤粺
            if (osName.contains("win")) {
                process = Runtime.getRuntime().exec(cmd);
            } else {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
            }
            // 鎵цffmpeg鎸囦护
            // 鍙栧嚭杈撳嚭娴佸拰閿欒娴佺殑淇℃伅
            // 娉ㄦ剰锛氬繀椤昏鍙栧嚭ffmpeg鍦ㄦ墽琛屽懡浠よ繃绋嬩腑浜х敓鐨勮緭鍑轰俊鎭紝濡傛灉涓嶅彇鐨勮瘽褰撹緭鍑烘祦淇℃伅濉弧jvm瀛樺偍杈撳嚭鐣欎俊鎭殑缂撳啿鍖烘椂锛岀嚎绋嬪氨鍥為樆濉炰綇
            PrintStream errorStream = new PrintStream(process.getErrorStream());
            PrintStream inputStream = new PrintStream(process.getInputStream());
            errorStream.start();
            inputStream.start();
            // 绛夊緟ffmpeg鍛戒护鎵ц瀹?
            process.waitFor();
            // 鑾峰彇鎵ц缁撴灉瀛楃涓?
            String result = errorStream.stringBuffer.append(inputStream.stringBuffer + "\n").toString();
            // 杈撳嚭鎵ц鐨勫懡浠や俊鎭?
            if (showLog) {
                logger.info("鎵ц鍛戒护{}缁撴灉{}", cmd, result);
            }
            return result;
        } catch (Exception e) {
            logger.error("鎵ц鍛戒护澶辫触cmd{}澶辫触:{} ", cmd, e.getMessage());
            throw new BusinessException("瑙嗛杞崲澶辫触");
        } finally {
            if (null != process) {
                ProcessKiller ffmpegKiller = new ProcessKiller(process);
                runtime.addShutdownHook(ffmpegKiller);
            }
        }
    }

    /**
     * 鍦ㄧ▼搴忛€€鍑哄墠缁撴潫宸叉湁鐨凢Fmpeg杩涚▼
     */
    private static class ProcessKiller extends Thread {
        private Process process;

        public ProcessKiller(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            this.process.destroy();
        }
    }


    /**
     * 鐢ㄤ簬鍙栧嚭ffmpeg绾跨▼鎵ц杩囩▼涓骇鐢熺殑鍚勭杈撳嚭鍜岄敊璇祦鐨勪俊鎭?
     */
    static class PrintStream extends Thread {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                if (null == inputStream) {
                    return;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
            } catch (Exception e) {
                logger.error("璇诲彇杈撳叆娴佸嚭閿欎簡锛侀敊璇俊鎭細" + e.getMessage());
            } finally {
                try {
                    if (null != bufferedReader) {
                        bufferedReader.close();
                    }
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    logger.error("璋冪敤PrintStream璇诲彇杈撳嚭娴佸悗锛屽叧闂祦鏃跺嚭閿欙紒");
                }
            }
        }
    }
}

