# Create ECS Policy
resource "aws_iam_policy" "ecs_policy" {
  name = var.iam_ecs_policy_name

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:DescribeLogGroups",
          "logs:PutLogEvents",
          "logs:FilterLogEvents",
          "ecr:GetAuthorizationToken",
          "ecr:BatchCheckLayerAvailability",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage",
        ]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
}

# Create DynamoDB Policy
resource "aws_iam_policy" "dynamodb_policy" {
  name = var.iam_dynamodb_policy_name

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "dynamodb:BatchGetItem",
          "dynamodb:Describe*",
          "dynamodb:List*",
          "dynamodb:GetItem",
          "dynamodb:Query",
          "dynamodb:Scan",
          "dynamodb:PutItem",
          "dynamodb:UpdateItem",
          "dynamodb:DeleteItem",
          "dynamodb:BatchWriteItem"
        ]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
}

# Create IAM Role
resource "aws_iam_role" "ecs_role" {
  name = var.role_name

  assume_role_policy = jsonencode({
    Version = "2008-10-17"
    Statement = [
      {
        Sid    = "",
        Effect = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      }
    ]
  })
}

# Attach the ECS policy to the IAM role
resource "aws_iam_policy_attachment" "ecs_role_policy_attachment" {
  name       = "Policy Attachement ECS"
  policy_arn = aws_iam_policy.ecs_policy.arn
  roles      = [aws_iam_role.ecs_role.name]
}

# Attach the Dynamodb policy to the IAM role
resource "aws_iam_policy_attachment" "dynamodb_role_policy_attachment" {
  name       = "Policy Attachement DynamoDB"
  policy_arn = aws_iam_policy.dynamodb_policy.arn
  roles      = [aws_iam_role.ecs_role.name]
}
