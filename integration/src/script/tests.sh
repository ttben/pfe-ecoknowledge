#!/usr/bin/env bash
echo
echo // ----- Launching tests
echo // ----- Launching tests >> "$LOG_FILE"

cd ../..

mvn test -DskipTests=false >> "$INTEGRATION_HOME/$LOG_FILE"

cd $INTEGRATION_HOME