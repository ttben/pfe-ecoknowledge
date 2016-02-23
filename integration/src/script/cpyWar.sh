#!/usr/bin/env bash
echo
echo // ----- Copying war

rm "$CATALINA_HOME/webapps/$SOURCE_WAR_NAME" >> "$LOG_FILE" 2>> "$LOG_FILE"
rm -rf "$CATALINA_HOME/webapps/$SOURCE_NAME" >> "$LOG_FILE" 2>> "$LOG_FILE"
cp "target/$SOURCE_WAR_NAME" "$CATALINA_HOME/webapps/$SOURCE_WAR_NAME" >> "$LOG_FILE" 2>> "$LOG_FILE"
