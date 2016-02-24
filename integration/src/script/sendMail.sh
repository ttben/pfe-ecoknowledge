#!/usr/bin/env bash
echo
echo // ----- Send Mail

subject=$(head "$INTEGRATION_HOME/$LOG_FILE")
addresses=petillon.sebastien@gmail.com

echo addresses : $addresses

cd ~
java -jar Mail.jar $subject $addresses

cd $INTEGRATION_HOME