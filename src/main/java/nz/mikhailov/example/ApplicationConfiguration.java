package nz.mikhailov.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
@EnableDynamoDBRepositories(basePackages = "nz.mikhailov.example.customer")
public class ApplicationConfiguration {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

		if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
			amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		}

		return amazonDynamoDB;
	}

	@Bean
	public DynamoDBMapper dynamoDbMapper(AmazonDynamoDB amazonDynamoDB) {

		log.trace("Entering dynamoDbMapper()");
		return new DynamoDBMapper(amazonDynamoDB);
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}
}
