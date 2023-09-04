package br.com.client.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime

class CreatedAtConverter : DynamoDBTypeConverter<String, LocalDateTime> {

    override fun convert(data: LocalDateTime): String {
        return data.toString()
    }

    override fun unconvert(data: String?): LocalDateTime {
        return LocalDateTime.parse(data)
    }

}