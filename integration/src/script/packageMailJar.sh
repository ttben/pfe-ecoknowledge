#!/usr/bin/env bash
echo
echo // ----- Copy mail jar
echo // ----- Copy mail jar >> "$INTEGRATION_HOME/$LOG_FILE"

cd ../..

rm $INTEGRATION_HOME/mail.jar >> /dev/null 2>> /dev/null
cp target/integration-1.0-SNAPSHOT-jar-with-dependencies.jar $INTEGRATION_HOME/mail.jar >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"

cd $INTEGRATION_HOME

