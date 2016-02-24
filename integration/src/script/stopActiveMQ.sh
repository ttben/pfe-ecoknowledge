#!/usr/bin/env bash
echo
echo // ----- Stopping ActiveMQ
echo // ----- Stopping ActiveMQ >> "$LOG_FILE"

"$ACTIVEMQ_HOME/bin/linux-x86-64/activemq" stop >> /dev/null 2>>"$LOG_FILE_ACTIVEMQ"
