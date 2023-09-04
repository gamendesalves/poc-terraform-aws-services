output "vpc_id" {
  description = "VPC ID created"
  value       = aws_vpc.poc.id
}

output "private_subnets" {
  description = "Ids Private Subnetes"
  #value       = [for subnet in aws_subnet.private_subnets : subnet.id]
  value = aws_subnet.private_subnets.*.id
}

output "public_subnets" {
  description = "Ids Public Subnetes"
  #value       = aws_subnet.public_subnets[*].id
  value = aws_subnet.public_subnets.*.id
}

output "internet_gateway_id" {
  description = "Id Internet Gateway"
  value       = aws_internet_gateway.gw.id
}
