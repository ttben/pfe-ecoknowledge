#!/usr/bin/env bash
echo
echo // ----- Launching tests

cd ../..

mvn test -DskipTests=false >> "$INTEGRATION_HOME/$LOG_FILE"

cd $INTEGRATION_HOME