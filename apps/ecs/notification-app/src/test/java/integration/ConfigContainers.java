package integration;

import br.com.notification.NotificationApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SES;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NotificationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ConfigContainers {

    private static DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack");

    @Container
    public static LocalStackContainer localStackContainer = new LocalStackContainer(LOCALSTACK_IMAGE)
            .withClasspathResourceMapping("/localstack", "/etc/localstack/init/ready.d", BindMode.READ_ONLY)
            .withServices(SQS, SES);

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("cloud.aws.sqs.endpoint", localStackContainer::getEndpoint);
        registry.add("cloud.aws.sqs.region", localStackContainer::getRegion);
        registry.add("aws.ses.endpoint", localStackContainer::getEndpoint);
        registry.add("aws.ses.region", localStackContainer::getRegion);
    }

    @Test
    void testContainersRunning() {
        assertTrue(postgreSQLContainer.isRunning());
        assertTrue(localStackContainer.isRunning());
    }

}
