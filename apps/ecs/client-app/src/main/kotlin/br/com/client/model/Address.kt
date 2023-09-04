package br.com.client.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
data class Address(
    @DynamoDBAttribute(attributeName = "Street")
    var street: String? = "",
    @DynamoDBAttribute(attributeName = "Number")
    var number: Int? = 0
) {
}