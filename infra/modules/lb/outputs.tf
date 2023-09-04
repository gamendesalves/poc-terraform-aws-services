output "alb_dns_name" {
  value = aws_lb.poc_alb.dns_name
  description = "ALB DNS Name Created"
}

output "alb_id" {
  value = aws_lb.poc_alb.id
}

output "alb_tg_id" {
  value = aws_lb_target_group.poc_alb_tg.id
}

output "nlb_dns_name" {
  value = aws_lb.poc_nlb.dns_name
  description = "NLB DNS Name Created"
}

output "nlb_arn" {
  value = aws_lb.poc_nlb.arn
}
