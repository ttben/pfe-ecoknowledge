#!/usr/bin/env bash
echo
echo // ----- Checking Tomcat

curl -get "$TOMCAT_HOST$TOMCAT_SERVICE" >> "$LOG_FILE"
curl -get "$TOMCAT_HOST$TOMCAT_SERVICE" > "$INTEGRATION_HOME/tomcatResponse"

firstLine=$(head -n 1 "$INTEGRATION_HOME/tomcatResponse")

if [ "$firstLine" !=  "{\"testOk\":true}" ]
then
	echo ERROR : Tomcat cant be reached
    echo ERROR : Tomcat cant be reached >> "$LOG_FILE"
	exit 1
fi

echo // Tomcat Avalaible
echo // Tomcat Avalaible >> "$LOG_FILE"

rm -rf "$INTEGRATION_HOME/tomcatResponse" >> "$LOG_FILE" 2>> "$LOG_FILE"