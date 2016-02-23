#!/usr/bin/env bash
echo
echo // ----- Stopping tomcat

echo /---------- Stopping Tomcat -----------/ >> "$LOG_FILE_TOMCAT"
"$CATALINA_HOME/bin/shutdown.sh" >> "$LOG_FILE_TOMCAT" 2>> "$LOG_FILE_TOMCAT"
ECHO /--------------------------------------/ >> "$LOG_FILE_TOMCAT"
ECHO >> "$LOG_FILE_TOMCAT"