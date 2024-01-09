import br.com.client.ClientAppApplication
import br.com.client.ContainerConfigs
import br.com.client.model.Address
import br.com.client.model.Client
import br.com.client.repository.ClientRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ClientAppApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test") // Used to configure DynamoDBConfig class
class ClientControllerTest(@Autowired val mockMvc: MockMvc) : ContainerConfigs() {

    @MockkBean
    lateinit var repository: ClientRepository

    @Test
    fun `Get all clients`() {
        every { repository.findAll(any<PageRequest>()) } returns PageImpl(listOf(getClient(), getClient("Pedro")))

        mockMvc.perform(
            get("/clients")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.content.[0].name").value("Gabriel"))
            .andExpect(jsonPath("\$.content.[1].name").value("Pedro"))
    }

    @Test
    fun `Get client by Id`() {
        val client = getClient()
        every { repository.findByIdOrNull(client.id ?: "") } returns client

        mockMvc.perform(
            get("/clients/".plus(client.id))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.name").value("Gabriel"))
    }

    @Test
    fun `Create client`() {
        val client = getClient()
        every { repository.save(any()) } returns client

        mockMvc.perform(
            post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(client))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.name").value("Gabriel"))
    }

    @Test
    fun `Update client`() {
        val client = getClient()
        every { repository.save(any()) } returns client

        mockMvc.perform(
            patch("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(client))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.name").value("Gabriel"))
    }

    @Test
    fun `Delete client by Id`() {
        val client = getClient()
        every { repository.deleteById(client.id ?: "") } returns Unit

        mockMvc.perform(
            delete("/clients/".plus(client.id))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    fun getClient(name: String = "Gabriel") =
        Client(UUID.randomUUID().toString(), LocalDateTime.now().toString(), name, 26, Address())
}
