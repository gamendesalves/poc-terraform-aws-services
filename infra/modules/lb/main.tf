# Create Application Load Balancer
resource "aws_lb" "poc_alb" {
  name            = var.name_alb
  security_groups = [var.lb_sg_id]
  subnets         = var.private_subnets_ids

  tags = {
    Environment = "alb-poc"
  }
}

# Create Target Group
resource "aws_lb_target_group" "poc_alb_tg" {
  name        = var.name_tg
  port        = 8080
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  # Configurations to know if application is health
  health_check {
    path    = var.health_check_tg
    timeout = 3
    matcher = "200"
  }

  lifecycle {
    create_before_destroy = true
  }
}

# Create Listener to redirect requests to TG
resource "aws_lb_listener" "backend_end" {
  load_balancer_arn = aws_lb.poc_alb.arn
  protocol          = "HTTP"
  port              = 80

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.poc_alb_tg.arn
  }
}

# Create Network Load Balancer
resource "aws_lb" "poc_nlb" {
  name               = var.name_nlb
  internal           = true
  load_balancer_type = "network"
  security_groups = [var.lb_sg_id]
  subnets            = var.private_subnets_ids
}

# Create Target Group pointing to ALB
resource "aws_lb_target_group" "poc_nlb_tg" {
  name        = var.name_nlb_tg
  port        = 80
  protocol    = "TCP"
  target_type = "alb"
  vpc_id      = var.vpc_id

  # Configurations to know if application is health
  health_check {
    path    = var.health_check_tg
    timeout = 3
    matcher = "200"
  }
}

# Attach the ALB to this target group
resource "aws_lb_target_group_attachment" "poc_nlb_tg_attachment" {
  target_group_arn = aws_lb_target_group.poc_nlb_tg.arn
  target_id        = aws_lb.poc_alb.id
  port             = aws_lb_listener.backend_end.port
}

# Create Listener to redirect requests to TG
resource "aws_lb_listener" "nlb_listener" {
  load_balancer_arn = aws_lb.poc_nlb.arn
  protocol          = "TCP"
  port              = 80

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.poc_nlb_tg.arn
  }
}
