# Create API Gateway
resource "aws_api_gateway_rest_api" "my_api" {
  name        = var.name_gtw
  description = var.description_gtw
}

# Create Resource (Default: client)
resource "aws_api_gateway_resource" "my_resouce" {
  rest_api_id = aws_api_gateway_rest_api.my_api.id
  parent_id   = aws_api_gateway_rest_api.my_api.root_resource_id
  path_part   = var.resource_name_gtw
}

resource "aws_api_gateway_resource" "my_resource_path_param" {
  rest_api_id = aws_api_gateway_rest_api.my_api.id
  parent_id   = aws_api_gateway_resource.my_resouce.id
  path_part   = "{id_client}"
}

resource "aws_api_gateway_method" "my_method" {
  for_each = var.http_methods_type

  rest_api_id   = aws_api_gateway_rest_api.my_api.id
  resource_id   = aws_api_gateway_resource.my_resouce.id
  http_method   = each.key
  authorization = "NONE"

  request_parameters = {
    "method.request.path.proxy" = true
  }
}

resource "aws_api_gateway_method" "my_method_path_param" {
  for_each = var.http_methods_type_path_param

  rest_api_id   = aws_api_gateway_rest_api.my_api.id
  resource_id   = aws_api_gateway_resource.my_resource_path_param.id
  http_method   = each.key
  authorization = "NONE"

  request_parameters = {
    "method.request.path.id_client" = true
  }
}

resource "aws_api_gateway_integration" "client_integration" {
  for_each    = var.http_methods_type

  rest_api_id = aws_api_gateway_rest_api.my_api.id
  resource_id = aws_api_gateway_resource.my_resouce.id
  http_method = aws_api_gateway_method.my_method[each.key].http_method

  type                    = "HTTP_PROXY"
  integration_http_method = aws_api_gateway_method.my_method[each.key].http_method
  uri                     = "http://${var.nlb_dns}/clientapp/clients"

  connection_type = "VPC_LINK"
  connection_id   = aws_api_gateway_vpc_link.my_vpc_link.id
}

resource "aws_api_gateway_integration" "client_integration_path_param" {
  for_each    = var.http_methods_type_path_param

  rest_api_id = aws_api_gateway_rest_api.my_api.id
  resource_id = aws_api_gateway_resource.my_resource_path_param.id
  http_method = aws_api_gateway_method.my_method_path_param[each.key].http_method

  type                    = "HTTP_PROXY"
  integration_http_method = aws_api_gateway_method.my_method_path_param[each.key].http_method
  uri                     = "http://${var.nlb_dns}/clientapp/clients/{id_client}"

  request_parameters = {
    "integration.request.path.id_client" = "method.request.path.id_client"
  }

  connection_type = "VPC_LINK"
  connection_id   = aws_api_gateway_vpc_link.my_vpc_link.id
}

# VPC Link
resource "aws_api_gateway_vpc_link" "my_vpc_link" {
  name        = var.name_vpc_link
  description = var.description_vpc_link
  target_arns = [var.nlb_arn]
}
