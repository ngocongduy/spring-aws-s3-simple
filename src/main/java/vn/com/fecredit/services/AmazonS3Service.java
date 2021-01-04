package vn.com.fecredit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import vn.com.fecredit.config.ConfigurationComponent;
import vn.com.fecredit.config.StaticContextAccessor;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Service
public class AmazonS3Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3Service.class);
    private final String endpointUrl;
    private final String bucketName;
    private final String accessKey;
    private final String secretKey;
    private S3Client s3client;

    public AmazonS3Service(String endpointUrl, String bucketName, String accessKey, String secretKey) {
        this.endpointUrl = endpointUrl;
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public AmazonS3Service() {
        this.endpointUrl = StaticContextAccessor.getBean(ConfigurationComponent.class).getS3EndpointUrl();
        this.bucketName = StaticContextAccessor.getBean(ConfigurationComponent.class).getS3BucketName();
        this.accessKey = StaticContextAccessor.getBean(ConfigurationComponent.class).getAwsAccessKey();
        this.secretKey = StaticContextAccessor.getBean(ConfigurationComponent.class).getAwsSecretKey();
        LOGGER.info(accessKey);
        LOGGER.info(secretKey);
    }

    @PostConstruct
    private void initializeAmazon() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);
        Region region = Region.AP_SOUTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
        s3client = s3;
    }

    public String presignFile(String fileKey) {
        LOGGER.info("Generating pre-signed URL.");
        String url = getPresignedUrl(bucketName, fileKey, 60);
        return url;
    }

    private String getPresignedUrl(String bucketName, String keyName, int expireMinute) {
        String url = "";
        try {
            AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);
            Region region = Region.AP_SOUTHEAST_1;
            S3Presigner presigner = S3Presigner.builder()
                    .region(region)
                    .credentialsProvider(StaticCredentialsProvider.create(creds)).build();
            GetObjectRequest getObjectRequest =
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build();
            GetObjectPresignRequest getObjectPresignRequest =
                    GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(expireMinute))
                            .getObjectRequest(getObjectRequest)
                            .build();

            // Generate the presigned request
            PresignedGetObjectRequest presignedGetObjectRequest =
                    presigner.presignGetObject(getObjectPresignRequest);

            // Log the presigned URL
            System.out.println("Presigned URL: " + presignedGetObjectRequest.url());
            url = presignedGetObjectRequest.url().toString();
            presigner.close();
        }
        catch (S3Exception e) {
            e.getStackTrace();
            LOGGER.debug(e.getMessage());
        }

        return url;
    }

}


