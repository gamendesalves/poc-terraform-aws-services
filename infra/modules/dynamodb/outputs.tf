output "dynamodb_table_client_arn" {
  description = "Table client Arn"
  value       = aws_dynamodb_table.client_app_table.arn
}

output "dynamodb_table_client_stream_arn" {
  description = "Table client Stream Arn"
  value       = aws_dynamodb_table.client_app_table.stream_arn
}