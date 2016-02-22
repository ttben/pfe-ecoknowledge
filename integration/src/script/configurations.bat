ECHO Configurations ...
SET datetimef=%date:~-4%_%date:~3,2%_%date:~0,2%
SET LOG_FILE=logs\%datetimef%.log
SET LOG_FILE_TOMCAT_START=logs\%datetimef%_tomcat_start.log
SET LOG_FILE_TOMCAT_STOP=logs\%datetimef%_tomcat_stop.log
SET LOG_FILE_ACTIVEMQ=logs\%datetimef%_activeMQ.log
SET SOURCE_MODULE_RELATIVE_PATH=..\..\..\fakeDataSource
SET SOURCE_NAME=fakeDataSource
SET SOURCE_WAR_NAME=fakeDataSource.war
SET TOMCAT_HOST=http://localhost:8080/
SET TOMCAT_SERVICE=fakeDataSource/
SET ACTIVEMQ_HOST=http://localhost:8161/
SET TIMEOUT_TOMCAT=30
SET TIMEOUT_ACTIVEMQ=40