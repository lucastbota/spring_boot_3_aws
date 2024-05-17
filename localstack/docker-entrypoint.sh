#!/bin/bash
aws configure set aws_access_key_id key
aws configure set aws_secret_access_key secret101
aws configure set region sa-east-1
aws configure set output table


## Create Bucket
awslocal s3api create-bucket --bucket customer-bureau --create-bucket-configuration LocationConstraint=sa-east-1
awslocal s3api create-bucket --bucket watt-bureau --create-bucket-configuration LocationConstraint=sa-east-1

## Create Parameter Store
aws --endpoint-url=http://localhost:4566 ssm put-parameter --name "/config/energy-sa-report/customer.api.id" --type String --value "customer123"
aws --endpoint-url=http://localhost:4566 ssm put-parameter --name "/config/energy-sa-report/invoice.api.id" --type String --value "invoice123"


## Create Secret Manager
aws --endpoint-url=http://localhost:4566 secretsmanager create-secret --name "/secrets/energy-sa-report"   --description "Energy SA external API secrets"  --secret-string file:///opt/localstack/secrets.json
aws --endpoint-url=http://localhost:4566 secretsmanager describe-secret --secret-id "/secrets/energy-sa-report" 

## Create SQS
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name kwh-collector

## Create DynamoDB
awslocal dynamodb create-table --cli-input-json '{ "TableName": "invoice_report", "KeySchema": [ { "AttributeName": "invoice_id", "KeyType": "HASH" } ], "AttributeDefinitions": [ { "AttributeName": "invoice_id", "AttributeType": "N" } ], "BillingMode": "PAY_PER_REQUEST" }'

sleep 10
aws --endpoint-url=http://localstack:4566 s3api list-buckets

## Lambda Init
##IAM policy
aws iam create-policy --policy-name my-pol --policy-document file:///opt/localstack/pol.txt --endpoint-url http://localhost:4566
##  execution role
aws iam create-role --role-name lambda-s3-role --assume-role-policy-document file:///opt/localstack/lambda-s3-role-policy.json --endpoint-url http://localhost:4566
## Correlate them
aws iam attach-role-policy --policy-arn arn:aws:iam::000000000000:policy/my-pol --role-name lambda-s3-role --endpoint-url http://localhost:4566
## Lambda Itself
aws lambda create-function --function-name CustomerHouse --zip-file fileb:///opt/localstack/function.zip --handler io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest --runtime java17 --timeout 10 --memory-size 1024 --role arn:aws:iam::000000000000:role/lambda-s3-role --endpoint-url http://localhost:4566
sleep 30
## S3 and lambda
aws s3api put-bucket-notification-configuration --bucket customer-bureau --notification-configuration file:///opt/localstack/notification.json --endpoint-url http://localhost:4566
