#!/usr/bin/env bash

echo 'Creating SQS Queue'
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name notification-client-queue --region us-east-1

#echo 'Listing SQS Queues'
#aws --endpoint-url=http://localhost:4566 sqs list-queues

#echo 'Publishing message SQS Queue'
#aws --endpoint-url=http://localhost:4566 sqs send-message --queue-url http://localhost:4566/000000000000/notification-client-queue --message-body "{\"id_client\":\"12345\",\"name\":\"Gabriel\",\"email\":\"gabriel@gmail.com.br\"}"

echo 'Creating Secrets Manager'
aws --endpoint-url=http://localhost:4566 secretsmanager create-secret --name passwd-db-secrets --secret-string "{\"passwd_db\":\"admin\"}" --region us-east-1

echo 'Creating ssm'
aws --endpoint http://localhost:4566 ssm put-parameter --name "/config/db/user_db" --value "admin" --type String --region us-east-1
aws --endpoint http://localhost:4566 ssm put-parameter --name "/config/email/sender_ses" --value "gabrielmendesalves120@gmail.com" --type String --region us-east-1

echo 'Creating and Verifying identities SES'
aws --endpoint http://localhost:4566 ssm put-parameter ses verify-email-identity --email gabrielmendesalves120@gmail.com
