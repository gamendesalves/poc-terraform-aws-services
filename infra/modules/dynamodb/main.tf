# Create Dynamodb Table
resource "aws_dynamodb_table" "client-app-table" {
  name         = var.table_name
  billing_mode = var.table_billing_mode
  hash_key     = "ClientId"

  attribute {
    name = "ClientId"
    type = "S"
  }

  tags = {
    Name = "dynamodb-table-client"
  }
}
