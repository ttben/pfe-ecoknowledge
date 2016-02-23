#!/usr/bin/env bash
echo
echo // ----- Starting ActiveMQ

echo /---------- Starting ActiveMQ -----------/ >> "$LOG_FILE_ACTIVEMQ"
"$ACTIVEMQ_HOME/bin/activemq" start >> "$LOG_FILE_ACTIVEMQ" 2>>"$LOG_FILE_ACTIVEMQ"
echo /--------------------------------------/ >> "$LOG_FILE_ACTIVEMQ"
echo >> "$LOG_FILE_ACTIVEMQ"
