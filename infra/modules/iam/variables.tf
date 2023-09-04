variable "iam_ecs_policy_name" {
  type    = string
  default = "my-poc-ecs-policy"
}

variable "iam_dynamodb_policy_name" {
  type    = string
  default = "my-poc-dynamodb-policy"
}

variable "role_name" {
  type    = string
  default = "my-poc-ecs-role"
}