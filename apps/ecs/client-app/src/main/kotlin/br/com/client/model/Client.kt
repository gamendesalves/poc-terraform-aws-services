package br.com.client.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.time.LocalDateTime
import java.util.*

@DynamoDBTable(tableName = "Client")
data class Client(

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "ClientId")
    var id: String? = "",

    @DynamoDBAttribute(attributeName = "CreatedAt")
    var createdAt: String? = LocalDateTime.now().toString(),

    @DynamoDBAttribute(attributeName = "Name")
    var name: String? = "",

    @DynamoDBAttribute(attributeName = "Age")
    var age: Int? = 0,

    @DynamoDBAttribute(attributeName = "Address")
    var address: Address? = Address()
) {
    constructor(name: String, age: Int, address: Address) : this(UUID.randomUUID().toString(), LocalDateTime.now().toString(), name, age, address)

}