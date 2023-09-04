package br.com.client.service

import br.com.client.model.Client
import br.com.client.repository.ClientRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClientService(val repository: ClientRepository) {

    fun getClients(pageable: Pageable) = repository.findAll(pageable)

    fun getClientById(idClient: String) = repository.findByIdOrNull(idClient)

    fun create(client: Client) = repository.save(client)

    fun update(client: Client) = repository.save(client)

    fun delete(idClient: String) = repository.deleteById(idClient)
}