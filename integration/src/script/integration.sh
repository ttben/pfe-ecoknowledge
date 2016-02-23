#!/usr/bin/env bash

export INTEGRATION_HOME=$(pwd)

echo
echo // ----- Configurations

datetime="$(date +%Y-%m-%d_%H-%M-%S)"
export LOG_FILE="logs\\$datetime.log"
export LOG_FILE_TOMCAT="logs\\$datetime.tomcat.log"
export LOG_FILE_ACTIVEMQ="logs\\$datetime.activemq.log"
export SOURCE_MODULE_RELATIVE_PATH="../../../fakeDataSource"
export SOURCE_NAME="fakeDataSource"
export SOURCE_WAR_NAME="fakeDataSource.war"
export TOMCAT_HOST="http://localhost:8080/"
export TOMCAT_SERVICE="fakeDataSource/"
export ACTIVEMQ_HOST="http://localhost:8161/"
export TIMEOUT_TOMCAT=30
export TIMEOUT_ACTIVEMQ=40

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

# END
$INTEGRATION_HOME/stopTomcat.sh
$INTEGRATION_HOME/stopActiveMQ.sh
 