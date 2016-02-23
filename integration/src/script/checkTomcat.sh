#!/usr/bin/env bash
echo
echo // ----- Checking Tomcat

curl -get "$TOMCAT_HOST$TOMCAT_SERVICE" >> "$LOG_FILE"
curl -get "$TOMCAT_HOST$TOMCAT_SERVICE" > "$INTEGRATION_HOME/tomcatResponse"

if ! [ -s "$INTEGRATION_HOME/tomcatResponse" ]
then
	echo ERROR : Tomcat cant be reached
    echo ERROR : Tomcat cant be reached >> "$LOG_FILE"
	exit 1
fi

ECHO // Tomcat Avalaible
ECHO // Tomcat Avalaible >> "$LOG_FILE"

rm -rf "$INTEGRATION_HOME/tomcatResponse" >> "$LOG_FILE" 2>> "$LOG_FILE"