# Create VPC
resource "aws_vpc" "poc" {
  cidr_block = "10.0.0.0/16"

  # Enabling automatic hostname assigning
  enable_dns_hostnames = true

  tags = {
    Name = "vpc_poc"
  }
}

# Create private subnets
resource "aws_subnet" "private_subnets" {
  count = length(var.private_subnet_cidrs)

  vpc_id            = aws_vpc.poc.id
  cidr_block        = element(var.private_subnet_cidrs, count.index)
  availability_zone = element(var.azs, count.index)

  tags = {
    Name = "Private Subnet ${count.index + 1}"
  }
}

# Create public subnets
resource "aws_subnet" "public_subnets" {
  count = length(var.public_subnet_cidrs)

  vpc_id            = aws_vpc.poc.id
  cidr_block        = element(var.public_subnet_cidrs, count.index)
  availability_zone = element(var.azs, count.index)

  # Enabling automatic public IP assignment on instance launch!
  map_public_ip_on_launch = true

  tags = {
    Name = "Public Subnet ${count.index + 1}"
  }
}

# Create Internet Gateway to be used by VPC
resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.poc.id

  tags = {
    Name = "POC IG"
  }
}

# Route Table to Public Subnets
resource "aws_route_table" "public_subnets_rt" {
  vpc_id = aws_vpc.poc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gw.id
  }

  tags = {
    Name = "Route Table for Public Subnets"
  }
}

# Associate Public Subnets to Route Table
resource "aws_route_table_association" "rta_public_subnets" {
  count          = length(var.public_subnet_cidrs)
  subnet_id      = element(aws_subnet.public_subnets[*].id, count.index)
  route_table_id = aws_route_table.public_subnets_rt.id
}

# Creating an Elastic IP for the NAT Gateway
resource "aws_eip" "eip_ng" {
  count  = length(var.public_subnet_cidrs)
  domain = "vpc"
}

# Creating a NAT Gateway
resource "aws_nat_gateway" "nat_gateway" {
  count         = length(var.public_subnet_cidrs)
  allocation_id = element(aws_eip.eip_ng[*].id, count.index)
  subnet_id     = element(aws_subnet.public_subnets[*].id, count.index)

  tags = {
    Name = "Nat Gateway ${count.index + 1}"
  }
}

# Creating a Route Table for the Nat Gateway
resource "aws_route_table" "nat_gateway_rt" {
  count  = length(var.public_subnet_cidrs)
  vpc_id = aws_vpc.poc.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = element(aws_nat_gateway.nat_gateway[*].id, count.index)
  }

  tags = {
    Name = "Route Table for NAT Gateway ${count.index + 1}"
  }
}

# Associate Private Subnets to Route Table
resource "aws_route_table_association" "rta_private_subnets" {
  count          = length(var.private_subnet_cidrs)
  subnet_id      = element(aws_subnet.private_subnets[*].id, count.index)
  route_table_id = element(aws_route_table.nat_gateway_rt[*].id, count.index)
}
