#!/usr/bin/env bash
echo
echo // ----- Copying wars
echo // ----- Copying wars >> "$INTEGRATION_HOME/$LOG_FILE"

cd $SOURCE_MODULE_RELATIVE_PATH
rm "$CATALINA_HOME/webapps/$SOURCE_WAR_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
rm -rf "$CATALINA_HOME/webapps/$SOURCE_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
cp "target/$SOURCE_WAR_NAME" "$CATALINA_HOME/webapps/$SOURCE_WAR_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"

cd $INTEGRATION_HOME
cd $SOURCE_ECOKNOWLEDGE_MODULE_RELATIVE_PATH
rm "$CATALINA_HOME/webapps/$SOURCE_ECOKNOWLEDGE_WAR_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
rm -rf "$CATALINA_HOME/webapps/$SOURCE_ECOKNOWLEDGE_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"
cp "target/$SOURCE_ECOKNOWLEDGE_WAR_NAME" "$CATALINA_HOME/webapps/$SOURCE_ECOKNOWLEDGE_WAR_NAME" >> /dev/null 2>> "$INTEGRATION_HOME/$LOG_FILE"