#!/usr/bin/env bash
echo
echo // ----- Stopping mongoDB
echo // ----- Stopping mongoDB >> "$LOG_FILE"

mongod --dbpath "DB_LOCATION" --shutdown >> /dev/null 2>> "$LOG_FILE_TOMCAT"

rm -rf "DB_LOCATION"