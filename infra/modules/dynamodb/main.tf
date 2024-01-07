# Create Dynamodb Table
resource "aws_dynamodb_table" "client_app_table" {
  name             = var.table_name
  billing_mode     = var.table_billing_mode
  hash_key         = "ClientId"
  stream_enabled   = var.stream_enabled
  stream_view_type = "NEW_IMAGE"

  attribute {
    name = "ClientId"
    type = "S"
  }

  tags = {
    Name = "dynamodb-table-client"
  }
}
