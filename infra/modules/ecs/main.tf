# Create ECS Cluster
resource "aws_ecs_cluster" "ecs_poc_cluster" {
  name = var.name_cluster
}

# Create Task Definition
resource "aws_ecs_task_definition" "poc_task_definition" {
  family                   = "my-poc"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = 1024
  memory                   = 2048
  execution_role_arn       = var.iam_role_ecs_arn
  task_role_arn            = var.iam_role_ecs_arn

  runtime_platform {
    cpu_architecture = "X86_64"
    operating_system_family = "LINUX"
  }

  container_definitions = jsonencode([
    {
      "name" : "${var.container_name}",
      "image" : "869866535291.dkr.ecr.us-east-1.amazonaws.com/client-app",
      "essential" : true,
      "portMappings" : [
        {
          "containerPort" : "${var.container_port}",
          "hostPort" : "${var.container_port}"
        }
      ],
      "logConfiguration" : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-create-group" : "true",
          "awslogs-group" : "/ecs/poc/",
          "awslogs-region" : "${var.region}",
          "awslogs-stream-prefix" : "ecs"
        }
      }
    }
  ])
}

# TODO -- CONFIGURE AUTOSCALING
# Create ECS Service to run Tasks
resource "aws_ecs_service" "my_service_poc" {
  name            = "service-poc"
  cluster         = aws_ecs_cluster.ecs_poc_cluster.id
  task_definition = aws_ecs_task_definition.poc_task_definition.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = var.private_subnets_ids
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.alb_target_group_id
    container_name   = var.container_name
    container_port   = var.container_port
  }
}
