variable "iam_lambda_policy_arn" {
  type        = string
  description = "Arn policy Lambda"
}

variable "notification_queue_url" {
  type        = string
  description = "SQS Queue Url to be called by lambda"
}

variable "dynamodb_table_client_stream_arn" {
  type        = string
  description = "Dynamodb Table client Stream ARN"
}