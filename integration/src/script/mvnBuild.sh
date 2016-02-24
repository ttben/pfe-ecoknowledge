#!/usr/bin/env bash
echo
echo // ----- Maven build
echo // ----- Maven build >> "$INTEGRATION_HOME/$LOG_FILE"

mvn package >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"