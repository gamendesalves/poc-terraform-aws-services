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

# Create Lambda Policy
resource "aws_iam_policy" "lambda_policy" {
  name = var.iam_lambda_policy_name

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action   = ["logs:*"],
        Effect   = "Allow",
        Resource = ["arn:aws:logs:*:*:*"]
      },
      {
        Action = [
          "dynamodb:BatchGetItem",
          "dynamodb:GetItem",
          "dynamodb:GetRecords",
          "dynamodb:Scan",
          "dynamodb:Query",
          "dynamodb:GetShardIterator",
          "dynamodb:DescribeStream",
          "dynamodb:ListStreams"
        ],
        Effect = "Allow",
        Resource = [
          "arn:aws:dynamodb:*:*:*"
        ]
      },
      {
        Action = [
          "sqs:SendMessage",
          "sqs:ReceiveMessage"
        ]
        Effect   = "Allow"
        Resource = "${var.notification_client_queue_arn}"
      },
    ]
  })
}

# Create IAM Role ECS
resource "aws_iam_role" "ecs_role" {
  name = var.role_ecs_name

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

# Create IAM Role Lambda
resource "aws_iam_role" "lambda_role" {
  name = var.role_lambda_name

  assume_role_policy = jsonencode({
    Version = "2008-10-17"
    Statement = [
      {
        Sid    = "LambdaAssumeRole",
        Effect = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
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

# Attach the Lambda policy to the IAM role
resource "aws_iam_policy_attachment" "lambda_role_policy_attachment" {
  name       = "Policy Attachement Lambda"
  policy_arn = aws_iam_policy.lambda_policy.arn
  roles      = [aws_iam_role.lambda_role.name]
}
