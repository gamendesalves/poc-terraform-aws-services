## Create Lambda
resource "aws_lambda_function" "lambda_function" {
  function_name    = "process-dynamodb-records"
  filename         = data.archive_file.lambda_zip_file.output_path
  source_code_hash = data.archive_file.lambda_zip_file.output_base64sha256
  handler          = "handler.handler"
  role             = var.iam_lambda_policy_arn
  runtime          = "python3.8"

  lifecycle {
    create_before_destroy = true
  }

  environment {
    variables = {
      notification_queue_url  = "${var.notification_queue_url}"
    }
  }
}

## Code of lambda
data "archive_file" "lambda_zip_file" {
  output_path = "${path.module}/lambda_zip/lambda.zip"
  source_dir  = "${path.module}/../../../apps/lambda"
  excludes    = ["__init__.py", "*.pyc"]
  type        = "zip"
}

## Mapping event source that trigger lambda from Dynamodb Streams
resource "aws_lambda_event_source_mapping" "example" {
  event_source_arn  = var.dynamodb_table_client_stream_arn
  function_name     = aws_lambda_function.lambda_function.arn
  starting_position = "LATEST"
}
