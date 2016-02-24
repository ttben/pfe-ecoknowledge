#!/usr/bin/env bash
echo
echo // ----- Send Mail

addresses=petillon.sebastien@gmail.com

echo addresses : $addresses

cd ~
java -jar Mail.jar "$INTEGRATION_HOME/$LOG_FILE" $addresses

cd $INTEGRATION_HOME