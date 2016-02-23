#!/usr/bin/env bash
echo
echo // ----- Maven build

echo /---------- MVN build -----------/ >> "$LOG_FILE"
mvn clean install >> "$LOG_FILE"
echo /--------------------------------/ >> "$LOG_FILE"
echo >> "$LOG_FILE"
