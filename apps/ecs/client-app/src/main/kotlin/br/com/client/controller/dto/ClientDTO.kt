package br.com.client.controller.dto

import br.com.client.model.Address
import br.com.client.model.Client

data class ClientDTO(val name: String, val age: Int, val address: Address) {
    fun toClient(): Client {
        return Client(this.name, this.age, this.address)
    }
}