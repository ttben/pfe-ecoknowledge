#!/usr/bin/env bash
echo
echo // ----- Launching mongoDB

mongod --dbpath ~ & >> /dev/null 2>  "$INTEGRATION_HOME/$LOG_FILE"