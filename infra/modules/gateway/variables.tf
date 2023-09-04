variable "name_gtw" {
  type        = string
  description = "Name of API Gateway"
  default     = "My POC GTW"
}

variable "description_gtw" {
  type        = string
  description = "Description of resource API Gateway"
  default     = "PI Gateway to handle requests to our API"
}

variable "name_vpc_link" {
  type        = string
  description = "Name of VPC Link"
  default     = "My VPC Link"
}

variable "description_vpc_link" {
  type        = string
  description = "Description VPC Link"
  default     = "VPC Link to connect with ALB"
}

variable "resource_name_gtw" {
  type        = string
  description = "Name of resource API Gateway"
  default     = "clients"
}

variable "http_methods_type" {
  type        = set(string)
  description = "Http Methods Type"
  default     = ["GET", "POST", "PATCH"]
}

variable "http_methods_type_path_param" {
  type        = set(string)
  description = "Http Methods Type"
  default     = ["GET", "DELETE"]
}

variable "nlb_arn" {
  type        = string
  description = "NLB arn"
}

variable "nlb_dns" {
  type        = string
  description = "NLB dns"
}
