#!/usr/bin/env bash
echo
echo // ----- Stopping ActiveMQ

echo /---------- Stopping ActiveMQ -----------/ >> "$LOG_FILE_ACTIVEMQ"
"$ACTIVEMQ_HOME/bin/linux-x86-64/activemq" stop >> "$LOG_FILE_ACTIVEMQ" 2>>"$LOG_FILE_ACTIVEMQ"
echo /--------------------------------------/ >> "$LOG_FILE_ACTIVEMQ"
echo >> "$LOG_FILE_ACTIVEMQ"
