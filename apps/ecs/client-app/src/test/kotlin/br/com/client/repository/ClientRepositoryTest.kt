import br.com.client.ClientAppApplication
import br.com.client.model.Address
import br.com.client.model.Client
import br.com.client.repository.ClientRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ClientAppApplication::class])
@WebAppConfiguration
@ActiveProfiles("test")
@TestPropertySource(
    properties = [
        "amazon.dynamodb.endpoint=http://localhost:4566/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231"
    ]
)
class ClientRepositoryTest(@Autowired val repository: ClientRepository) {

    companion object {
        const val EXPECTED_NAME = "Gabriel"
    }

    @Test
    fun `When create Client and FindAll then return clients registered`() {
        repository.save(getClient())

        val result = repository.findAll(PageRequest.of(0, 5)).toList()
        assertThat(result.size, `is`(greaterThan(0)))
        assertThat(result[0].name, `is`(equalTo(EXPECTED_NAME)))
    }

    @Test
    fun `When create Client then remove Client successfully`() {
        repository.deleteAll()

        val client = repository.save(getClient())
        repository.deleteById(client.id ?: "")

        val result = repository.findAll(PageRequest.of(0, 5)).toList()
        assertThat(result.size, `is`(equalTo(0)))
    }

    fun getClient() = Client(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "Gabriel", 26, Address())
}