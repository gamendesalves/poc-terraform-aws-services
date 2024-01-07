output "notification_queue_arn" {
  description = "Notification Queue Arn"
  value       = aws_sqs_queue.notification_client_queue.arn
}

output "notification_queue_url" {
  description = "Notification Queue Url"
  value       = aws_sqs_queue.notification_client_queue.url
}
