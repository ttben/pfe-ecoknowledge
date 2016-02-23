#!/usr/bin/env bash
echo
echo // ----- Starting tomcat

echo /---------- Starting Tomcat -----------/ >> "$LOG_FILE_TOMCAT"
"$CATALINA_HOME/bin/startup.sh" >> "$LOG_FILE_TOMCAT" 2>> "$LOG_FILE_TOMCAT"
echo /--------------------------------------/ >> "$LOG_FILE_TOMCAT"
echo >> "$LOG_FILE_TOMCAT"