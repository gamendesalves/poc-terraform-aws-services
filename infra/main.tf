# Create VPcs and Subnets
module "vpc" {
  source = "./modules/vpc"
}

module "sg" {
  source = "./modules/securitygroups"
  vpc_id = module.vpc.vpc_id
}

module "alb" {
  source             = "./modules/lb"
  vpc_id             = module.vpc.vpc_id
  private_subnets_ids = module.vpc.private_subnets
  lb_sg_id          = module.sg.lb_sg_id
}

module "iam" {
  source = "./modules/iam"
}

module "ecs" {
  source              = "./modules/ecs"
  alb_target_group_id = module.alb.alb_tg_id
  ecs_sg_id           = module.sg.ecs_sg_id
  private_subnets_ids = module.vpc.private_subnets
  iam_role_ecs_arn    = module.iam.ecs_role_arn
  region              = "us-east-1"
}

module "dynamodb" {
  source = "./modules/dynamodb"
}

module "gtw" {
  source  = "./modules/gateway"
  nlb_arn = module.alb.nlb_arn
  nlb_dns = module.alb.nlb_dns_name
}

# resource "aws_ecr_repository" "client_app_repository" {
#   name = "client-app"
# }
