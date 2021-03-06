#!/usr/bin/env bash
echo
echo // ----- Checking Tomcat
echo // ----- Checking Tomcat >> "$LOG_FILE"

curl -get "$TOMCAT_HOST$TOMCAT_SERVICE" > "$INTEGRATION_HOME/tomcatResponse"

firstLine=$(head -n 1 "$INTEGRATION_HOME/tomcatResponse")

if [ "$firstLine" !=  "{\"testOk\":true}" ]
then
	echo ERROR : Tomcat cant be reached
    echo ERROR : Tomcat cant be reached ... FAIL >> "$INTEGRATION_HOME/$LOG_FILE"
	exit 1
fi

curl -get "$TOMCAT_HOST$ECOKNOWLEDGE_SERVICE" >> "$INTEGRATION_HOME/$LOG_FILE"
echo >> "$LOG_FILE"
curl -get "$TOMCAT_HOST$ECOKNOWLEDGE_SERVICE" > "$INTEGRATION_HOME/ecoknowledgeResponse"

firstLineEcoknowledge=$(head -n 1 "$INTEGRATION_HOME/ecoknowledgeResponse")

if [[ $firstLineEcoknowledge == *"Tables"* ]]
then
	echo ERROR : Ecoknowledge cant be reached
    echo ERROR : Ecoknowledge cant be reached ... FAIL >> "$INTEGRATION_HOME/$LOG_FILE"
	exit 1
fi

echo
echo // Tomcat Avalaible
echo // Tomcat Avalaible >> "$LOG_FILE"

rm -rf "$INTEGRATION_HOME/tomcatResponse" >> /dev/null 2>> "$LOG_FILE"
#rm -rf "$INTEGRATION_HOME/ecoknowledgeResponse" >> /dev/null 2>> "$LOG_FILE"