variable "name_ecr" {
  type        = string
  description = "Name ECR Repository"
  default     = "client-app"
}

variable "name_cluster" {
  type        = string
  description = "Name Cluster ECS"
  default     = "my-cluster"
}

variable "container_port" {
  type    = number
  default = 8080
}

variable "container_name" {
  type    = string
  default = "my-poc"
}

variable "region" {
  type    = string
  default = "us-east-1"
}

variable "alb_target_group_id" {
  type        = string
  description = "Alb Arn to used by ECS"
}

variable "ecs_sg_id" {
  type        = string
  description = "ECS Security Groud Id"
}

variable "private_subnets_ids" {
  type        = set(string)
  description = "Private Subnets IDs"
}

variable "iam_role_ecs_arn" {
  type        = string
  description = "Arn ECS Role"
}
