package br.com.client.repository

import br.com.client.model.Client
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

@EnableScan
@EnableScanCount
interface ClientRepository : PagingAndSortingRepository<Client, String> {
    override fun findAll(pageable: Pageable): Page<Client>
}