package br.com.client

import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@ExtendWith(SpringExtension::class)
@Testcontainers
@ContextConfiguration(classes = [ContainerConfigs::class])
open class ContainerConfigs {

    companion object {

        private val DYNAMODB_CONTAINER: LocalStackContainer =
            LocalStackContainer(DockerImageName.parse("localstack/localstack"))
                .withServices(LocalStackContainer.Service.DYNAMODB);

        init {
            DYNAMODB_CONTAINER.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("amazon.dynamodb.endpoint") { DYNAMODB_CONTAINER.endpoint }
            registry.add("amazon.aws.accesskey") { DYNAMODB_CONTAINER.accessKey }
            registry.add("amazon.aws.secretkey") { DYNAMODB_CONTAINER.secretKey }
        }

    }

    @Test
    fun `Test containers running`() {
        assertTrue(DYNAMODB_CONTAINER.isRunning)
    }
}