#!/usr/bin/env bash
echo
echo // ----- Starting tomcat
echo // ----- Starting tomcat >> "$LOG_FILE"

"$CATALINA_HOME/bin/startup.sh" >> /dev/null 2>> "$LOG_FILE_TOMCAT"