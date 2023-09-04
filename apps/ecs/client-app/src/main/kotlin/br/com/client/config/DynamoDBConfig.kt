package br.com.client.config


import br.com.client.model.Client
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.BillingMode
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import kotlin.reflect.KClass

@Configuration
@EnableDynamoDBRepositories("br.com.client.repository")
class DynamoDBConfig(
    @Value("\${aws.region}") private val amazonAwsRegion: String,
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Bean("amazonDynamoDB")
    @Profile(value = ["localhost", "test"])
    fun amazonDynamoDBLocal(
        @Value("\${amazon.dynamodb.endpoint:''}") amazonDynamoDBEndpoint: String,
        @Value("\${amazon.aws.accesskey}") amazonAccessKey: String,
        @Value("\${amazon.aws.secretkey}") amazonSecretKey: String,
    ): AmazonDynamoDB {
        var client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(amazonAccessKey, amazonSecretKey)))
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAwsRegion)
            ).build();

        this.createTableForEntity(client, Client::class)

        return client;
    }

    @Bean
    @Profile(value = ["!localhost", "!test"])
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard().withRegion(amazonAwsRegion).build()
    }

    private fun createTableForEntity(amazonDynamoDB: AmazonDynamoDB, entity: KClass<*>) {

        val tableRequest = DynamoDBMapper(amazonDynamoDB)
            .generateCreateTableRequest(entity.java)
            .withBillingMode(BillingMode.PAY_PER_REQUEST)

        try {
            DynamoDB(amazonDynamoDB).createTable(tableRequest).waitForActive()
            log.info("Table created! [entity={}]", entity)
        } catch (e: ResourceInUseException) {
            log.info("Table already exists - skip creation! [entity={}]", entity)
        }
    }
}