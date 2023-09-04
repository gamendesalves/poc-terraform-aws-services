output "ecs_sg_id" {
  description = "Security Group ECS ID created"
  value       = aws_security_group.ecs_sg.id
}

output "lb_sg_id" {
  description = "Security Group LB ID created"
  value       = aws_security_group.lb_sg.id
}

