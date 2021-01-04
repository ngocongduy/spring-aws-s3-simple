package vn.com.fecredit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationComponent {
	
	@Value("${roboApiKey}")
	private String roboApiKey;

	@Value("${defaultUser}")
	private String defaultUser;
	
	@Value("${defaultPassword}")
	private String defaultPassword;

	@Value("${amazonProperties.endpointUrl}")
	private String s3EndpointUrl;

	@Value("${amazonProperties.accessKey}")
	private String awsAccessKey;

	@Value("${amazonProperties.secretKey}")
	private String awsSecretKey;

	@Value("${amazonProperties.bucketName}")
	private String s3BucketName;

	public String getRoboApiKey() {
		return roboApiKey;
	}

	public String getDefaultUser() {
		return defaultUser;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public String getS3EndpointUrl() {
		return s3EndpointUrl;
	}

	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public String getS3BucketName() {
		return s3BucketName;
	}

	public void setRoboApiKey(String roboApiKey) {
		this.roboApiKey = roboApiKey;
	}

	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public void setS3EndpointUrl(String s3EndpointUrl) {
		this.s3EndpointUrl = s3EndpointUrl;
	}

	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}

	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

	public void setS3BucketName(String s3BucketName) {
		this.s3BucketName = s3BucketName;
	}
}
