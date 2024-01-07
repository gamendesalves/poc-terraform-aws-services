output "lambda_arn" {
  description = "Lambda Arn"
  value       = aws_lambda_function.lambda_function.arn
}
