#!/usr/bin/env bash
echo
echo // ----- Launching mongoDB
echo // ----- Launching mongoDB >> "$LOG_FILE"

mkdir -p "DB_LOCATION"

mongod --dbpath "DB_LOCATION" & >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"