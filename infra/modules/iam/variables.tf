variable "iam_ecs_policy_name" {
  type    = string
  default = "my-poc-ecs-policy"
}

variable "iam_dynamodb_policy_name" {
  type    = string
  default = "my-poc-dynamodb-policy"
}

variable "iam_lambda_policy_name" {
  type    = string
  default = "my-poc-lambda-policy"
}

variable "role_ecs_name" {
  type    = string
  default = "my-poc-ecs-role"
}

variable "role_lambda_name" {
  type    = string
  default = "my-poc-lambda-role"
}

variable "notification_client_queue_arn" {
   type    = string
   description = "ARN Notification Queue SQS"
}