#!/usr/bin/env bash

export INTEGRATION_HOME=$(pwd)

echo
echo // ----- Configurations

export datetime="$(date +%Y-%m-%d_%H-%M-%S)"
export LOG_FILE="logs/$datetime.log"
export LOG_FILE_TOMCAT="logs/$datetime.tomcat.log"
export LOG_FILE_ACTIVEMQ="logs/$datetime.activemq.log"

export SOURCE_MODULE_RELATIVE_PATH="../../../fakeDataSource"
export SOURCE_NAME="fakeDataSource"
export SOURCE_WAR_NAME="fakeDataSource.war"

export SOURCE_ECOKNOWLEDGE_MODULE_RELATIVE_PATH="../../../server"
export SOURCE_ECOKNOWLEDGE_WAR_NAME="Ecoknowledge.war"
export SOURCE_ECOKNOWLEDGE_NAME="server"

export SOURCE_WEBTIERSFEEDER_MODULE_RELATIVE_PATH="../../../feeder/webTierFeeder/"
export SOURCE_WEBTIERSFEEDER_WAR_NAME="webTierFeeder.war"
export SOURCE_WEBTIERSFEEDER_NAME="webTierFeeder"

export TOMCAT_HOST="http://nabnab.aloadae.feralhosting.com:8081/"

export TOMCAT_SERVICE="fakeDataSource/"

export ECOKNOWLEDGE_SERVICE="Ecoknowledge/test/db/names"
export ACTIVEMQ_HOST="http://nabnab.aloadae.feralhosting.com:8161/"
export TIMEOUT_TOMCAT=30
export TIMEOUT_ACTIVEMQ=30
export TIMEOUT_MONGODB=10
export DB_LOCATION="~/tmpDataDB"

export addresses=petillon.sebastien@gmail.com,benjamin.benni06@gmail.com

echo > "$LOG_FILE"

$INTEGRATION_HOME/checkEnvironment.sh


$INTEGRATION_HOME/startMongoDB.sh
sleep $TIMEOUT_MONGODB

$INTEGRATION_HOME/stopTomcat.sh
$INTEGRATION_HOME/startTomcat.sh

$INTEGRATION_HOME/cpyWars.sh

cd $INTEGRATION_HOME

sleep $TIMEOUT_TOMCAT

$INTEGRATION_HOME/checkTomcat.sh

$INTEGRATION_HOME/stopActiveMQ.sh
$INTEGRATION_HOME/startActiveMQ.sh

sleep $TIMEOUT_ACTIVEMQ

$INTEGRATION_HOME/checkActiveMQ.sh

$INTEGRATION_HOME/tests.sh

$INTEGRATION_HOME/packageMailJar.sh
$INTEGRATION_HOME/sendMail.sh

# END
$INTEGRATION_HOME/stopMongoDB.sh
$INTEGRATION_HOME/stopTomcat.sh
$INTEGRATION_HOME/stopActiveMQ.sh
