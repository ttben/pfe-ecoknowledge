#!/usr/bin/env bash

export INTEGRATION_HOME=$(pwd)

echo
echo // ----- Configurations

datetime="$(date +%Y-%m-%d_%H-%M-%S)"
export LOG_FILE="logs/$datetime.log"
export LOG_FILE_TOMCAT="logs/$datetime.tomcat.log"
export LOG_FILE_ACTIVEMQ="logs/$datetime.activemq.log"
export SOURCE_MODULE_RELATIVE_PATH="../../../fakeDataSource"
export SOURCE_NAME="fakeDataSource"
export SOURCE_WAR_NAME="fakeDataSource.war"
export TOMCAT_HOST="http://nabnab.aloadae.feralhosting.com:8081/"
export TOMCAT_SERVICE="fakeDataSource/"
export ACTIVEMQ_HOST="http://nabnab.aloadae.feralhosting.com:8161/"
export TIMEOUT_TOMCAT=10
export TIMEOUT_ACTIVEMQ=30
export TIMEOUT_MONGODB=10

export addresses=petillon.sebastien@gmail.com,benjamin.benni06@gmail.com

echo > "$LOG_FILE"

$INTEGRATION_HOME/checkEnvironment.sh
$INTEGRATION_HOME/stopTomcat.sh
$INTEGRATION_HOME/startTomcat.sh

cd $SOURCE_MODULE_RELATIVE_PATH

$INTEGRATION_HOME/mvnBuild.sh
$INTEGRATION_HOME/cpyWar.sh

cd $INTEGRATION_HOME

sleep $TIMEOUT_TOMCAT

$INTEGRATION_HOME/checkTomcat.sh

$INTEGRATION_HOME/stopActiveMQ.sh
$INTEGRATION_HOME/startActiveMQ.sh

sleep $TIMEOUT_ACTIVEMQ

$INTEGRATION_HOME/checkActiveMQ.sh

$INTEGRATION_HOME/launchMongo.sh

sleep $TIMEOUT_MONGODB

$INTEGRATION_HOME/tests.sh

$INTEGRATION_HOME/packageMailJar.sh
$INTEGRATION_HOME/sendMail.sh

# END
$INTEGRATION_HOME/stopTomcat.sh
$INTEGRATION_HOME/stopActiveMQ.sh
 