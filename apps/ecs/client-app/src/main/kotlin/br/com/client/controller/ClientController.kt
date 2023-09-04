package br.com.client.controller

import br.com.client.controller.dto.ClientDTO
import br.com.client.model.Client
import br.com.client.service.ClientService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/clients")
class ClientController(val service: ClientService) {

    @GetMapping
    fun getClients(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
    ) = service.getClients(PageRequest.of(page, size))

    @GetMapping("/{id_client}")
    fun getClientById(@PathVariable("id_client") idClient: String) =
        service.getClientById(idClient) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This client does not exist"
        )

    @PostMapping
    fun createClient(@Validated @RequestBody dto: ClientDTO) = service.create(dto.toClient())

    @PatchMapping
    fun updateClient(@Validated @RequestBody client: Client) = service.update(client)

    @DeleteMapping("/{id_client}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteClient(@PathVariable("id_client") idClient: String) = service.delete(idClient)
}