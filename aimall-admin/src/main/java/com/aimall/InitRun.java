package com.aimall;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component("initRun")
public class InitRun implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            redisTemplate.getConnectionFactory().getConnection().isClosed();
            logger.info("Admin鏈嶅姟鍚姩鎴愬姛锛屽彲浠ュ紑濮嬫剦蹇殑寮€鍙戜簡");
        } catch (SQLException e) {
            logger.error("鏁版嵁搴撻厤缃敊璇紝璇锋鏌ユ暟鎹簱閰嶇疆");
        } catch (Exception e) {
            logger.error("鏈嶅姟鍚姩澶辫触", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("鍏抽棴鏁版嵁搴撹繛鎺ュけ璐?);
                }
            }
        }
    }
}

