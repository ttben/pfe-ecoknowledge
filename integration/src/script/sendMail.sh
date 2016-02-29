#!/usr/bin/env bash
echo
echo // ----- Send Mail
echo // ----- Send Mail >> "$LOG_FILE"

echo addresses : $addresses

$JAVA_HOME/bin/java -jar mail.jar "$INTEGRATION_HOME/$LOG_FILE" $addresses
