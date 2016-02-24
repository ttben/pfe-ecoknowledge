#!/usr/bin/env bash
echo
echo // ----- Copying war
echo // ----- Copying war >> "$INTEGRATION_HOME/$LOG_FILE"

rm "$CATALINA_HOME/webapps/$SOURCE_WAR_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
rm -rf "$CATALINA_HOME/webapps/$SOURCE_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
cp "target/$SOURCE_WAR_NAME" "$CATALINA_HOME/webapps/$SOURCE_WAR_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
