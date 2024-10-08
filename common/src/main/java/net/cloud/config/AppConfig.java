package net.cloud.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Data
public class AppConfig {
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private String redisPort;
    @Value("${spring.redis.password}")
    private String redisPwd;
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setPassword(redisPwd).setAddress("redis://"+redisHost+":"+redisPort);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
