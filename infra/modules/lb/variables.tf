variable "name_nlb" {
  type        = string
  description = "Name NLB"
  default     = "poc-nlb"
}

variable "name_alb" {
  type        = string
  description = "Name ALB"
  default     = "poc-alb"
}

variable "name_tg" {
  type        = string
  description = "Name Target Group"
  default     = "poc-alb-tg"
}

variable "name_nlb_tg" {
  type        = string
  description = "Name Target Group NLB"
  default     = "poc-nlb-tg"
}

variable "health_check_tg" {
  type        = string
  description = "Path Health Check TG"
  default     = "/clientapp/actuator/health"
}

variable "private_subnets_ids" {
  type        = set(string)
  description = "Private Subnets IDs"
}

variable "vpc_id" {
  type        = string
  description = "VPC ID"
}

variable "lb_sg_id" {
  type        = string
  description = "Id Security Group LB"
}