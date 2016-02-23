#!/usr/bin/env bash
echo
echo // ----- Maven build

echo /---------- MVN build -----------/ >> "$INTEGRATION_HOME/$LOG_FILE"
mvn package >> "$INTEGRATION_HOME/$LOG_FILE" 2>> "$INTEGRATION_HOME/$LOG_FILE"
echo /--------------------------------/ >> "$INTEGRATION_HOME/$LOG_FILE"
echo >> "$INTEGRATION_HOME/$LOG_FILE"