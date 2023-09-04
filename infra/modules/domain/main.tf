resource "aws_route53_zone" "devopspoc_hosted_zoned" {
  name = var.domain_name
}

resource "aws_route53_record" "devops_record" {
  zone_id = aws_route53_zone.devopspoc_hosted_zoned.zone_id
  name    = tolist(aws_acm_certificate.devops_cert.domain_validation_options)[0].resource_record_name
  records = [tolist(aws_acm_certificate.devops_cert.domain_validation_options)[0].resource_record_value]
  type    = tolist(aws_acm_certificate.devops_cert.domain_validation_options)[0].resource_record_type
  ttl     = 60

  depends_on = [aws_acm_certificate.devops_cert]
}

resource "aws_acm_certificate" "devops_cert" {
  domain_name               = var.domain_name
  subject_alternative_names = [var.domain_name, "*.${var.domain_name}"]
  validation_method         = "DNS"

  tags = {
    Name = var.domain_name
  }

  lifecycle {
    create_before_destroy = true
  }
}

# resource "aws_acm_certificate_validation" "devops_cert_validation" {
#   certificate_arn         = aws_acm_certificate.devops_cert.arn
#   validation_record_fqdns = [aws_route53_record.devops_record.fqdn]
# }
