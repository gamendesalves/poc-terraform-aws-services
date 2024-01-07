import logging
import boto3
import json
import os
from boto3.dynamodb.types import TypeDeserializer

LOGGER = logging.getLogger()
LOGGER.setLevel(logging.INFO)
sqs = boto3.client('sqs')

def deserializeData(r, type_deserializer = TypeDeserializer()):
    return type_deserializer.deserialize({"M": r})

def handler(event, context):
   
    record = event['Records'][0]

    LOGGER.info(record)

    if record['eventName'] == 'INSERT':

        notificationQueueUrl = os.environ['notification_queue_url']

        dataClient = json.dumps(deserializeData(record['dynamodb']['NewImage']), indent=2)

        LOGGER.info('Sending to sqs queue ' + str(notificationQueueUrl) + " with data " + dataClient)

        sqs.send_message(
            QueueUrl=notificationQueueUrl,
            MessageBody=dataClient
        )

        return {
            'statusCode': 200,
            'body': json.dumps('Message was sent')
        }

    return {
        'statusCode': 200,
    }
   
    

   