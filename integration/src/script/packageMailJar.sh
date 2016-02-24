#!/usr/bin/env bash
echo
echo // ----- Maven package : mail
echo // ----- Maven package : mail >> "$INTEGRATION_HOME/$LOG_FILE"

cd ../..

mvn package >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"

rm $INTEGRATION_HOME/mail.jar >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
cp target/integration-1.0-SNAPSHOT-jar-with-dependencies.jar $INTEGRATION_HOME/mail.jar >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"

cd $INTEGRATION_HOME

