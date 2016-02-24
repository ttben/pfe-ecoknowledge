#!/usr/bin/env bash
echo
echo // ----- Check environment

if [ "$CATALINA_HOME" == "" ]
then
    echo "CATALINA_HOME is not set";
    echo ERROR : CATALINA_HOME is not defined ... FAIL >> "$LOG_FILE"
    exit 1
fi
if [ "$ACTIVEMQ_HOME" == "" ]
then
    echo "ACTIVEMQ_HOME is not set";
    echo ERROR : ACTIVEMQ_HOME is not defined ... FAIL >> "$LOG_FILE"
    exit 1
fi