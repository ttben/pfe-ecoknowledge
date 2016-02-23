#!/usr/bin/env bash
echo
echo // ----- Checking ActiveMQ

curl -get "$ACTIVEMQ_HOST" >> "$LOG_FILE"
curl -get "$ACTIVEMQ_HOST" > "$INTEGRATION_HOME/activeMQResponse"

if [[ "$firstLine" ==  "404" ]]
then
	echo ERROR : ActiveMQ cant be reached
    echo ERROR : ActiveMQ cant be reached >> "$LOG_FILE"
	exit 1
fi

echo // ActiveMQ Avalaible
echo // ActiveMQ Avalaible >> "$LOG_FILE"

rm -rf "$INTEGRATION_HOME/activeMQResponse" >> "$LOG_FILE" 2>> "$LOG_FILE"