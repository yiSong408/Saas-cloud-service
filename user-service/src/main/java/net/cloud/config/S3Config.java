package net.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "cloud.aws.credentials")
@Configuration
@Data
public class S3Config {
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String region;
    private String regionUri;

}
