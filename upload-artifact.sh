#!/bin/bash

ENV_FILE=src/main/resources/application.properties
CURL=/usr/bin/curl

if [ -v ENV_FILE ]
  then
    # shellcheck disable=SC2046
    export $(cat $ENV_FILE | xargs)
else
  echo "ENV_FILE is not valid"
  set -e
fi

curlCmd=$($CURL -u $JFROG_USER:$PASSWORD_OR_TOKEN -X PUT $PATH -T $JAR_PATH) && echo "Artifact uploaded successfully" || echo "Could not upload artifact. Verify your environment variables"