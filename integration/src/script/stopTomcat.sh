#!/usr/bin/env bash
echo
echo // ----- Stopping tomcat
echo // ----- Stopping tomcat >> "$LOG_FILE"

"$CATALINA_HOME/bin/shutdown.sh" >> /dev/null 2>> "$LOG_FILE_TOMCAT"