#!/usr/bin/env bash
echo
echo // ----- Send Mail

echo addresses : $addresses

$JAVA_HOME/bin/java -jar mail.jar "$INTEGRATION_HOME/$LOG_FILE" $addresses
