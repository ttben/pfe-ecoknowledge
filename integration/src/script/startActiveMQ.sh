#!/usr/bin/env bash
echo
echo // ----- Starting ActiveMQ
echo // ----- Starting ActiveMQ >> "$LOG_FILE"

"$ACTIVEMQ_HOME/bin/linux-x86-64/activemq" start >> /dev/null 2>>"$LOG_FILE_ACTIVEMQ"
