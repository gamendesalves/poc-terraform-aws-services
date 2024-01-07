output "ecs_role_arn" {
  description = "ECS Role Arn"
  value       = aws_iam_role.ecs_role.arn
}

output "lambda_role_arn" {
  description = "Lambda Role Arn"
  value       = aws_iam_role.lambda_role.arn
}
