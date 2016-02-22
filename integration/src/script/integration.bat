CLS
@ECHO OFF

ECHO Configurations ...
SET datetimef=%date:~-4%_%date:~3,2%_%date:~0,2%__%time:~0,2%_%time:~3,2%_%time:~6,2%
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
SET TIMEOUT=30

SET "INTEGRATION_HOME=%CD%"
ECHO INTEGRATION_HOME : %INTEGRATION_HOME% > %INTEGRATION_HOME%\%LOG_FILE%

IF ["%CATALINA_HOME%"] == [""] (
	ECHO ERROR : CATALINA_HOME is not defined >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : CATALINA_HOME is not defined
	GOTO :end
)

IF ["%CURL_HOME%"] == [""] (
    ECHO ERROR : CURL_HOME is not defined >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : CURL_HOME is not defined
	GOTO :end
)

ECHO /---------- Working Configurations -----------/ >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO SOURCE_MODULE_RELATIVE_PATH = %SOURCE_MODULE_RELATIVE_PATH% >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO /---------------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE%

ECHO Stopping Tomcat ...
ECHO /---------- Stopping Tomcat -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP%
CALL "%CATALINA_HOME%\bin\shutdown.bat" >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP%

ECHO Starting Tomcat ...
ECHO /---------- Starting Tomcat -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START%
CALL "%CATALINA_HOME%\bin\startup.bat"  >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START%

CD %SOURCE_MODULE_RELATIVE_PATH%
ECHO mvn build ...
ECHO /---------- MVN build -----------/ >> %INTEGRATION_HOME%\%LOG_FILE%
CALL mvn clean install >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO /--------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE%

IF NOT EXIST target\%SOURCE_WAR_NAME% (
	ECHO ERROR : %SOURCE_WAR_NAME% BUILD FAILED
	GOTO :end
)
ECHO INFO : %SOURCE_WAR_NAME% BUILD SUCCESS >> %INTEGRATION_HOME%\%LOG_FILE%

DEL "%CATALINA_HOME%\webapps\%SOURCE_WAR_NAME%" >> %INTEGRATION_HOME%\%LOG_FILE%
RMDIR /Q /S "%CATALINA_HOME%\webapps\%SOURCE_NAME%" >> %INTEGRATION_HOME%\%LOG_FILE%
COPY target\%SOURCE_WAR_NAME% "%CATALINA_HOME%\webapps\%SOURCE_WAR_NAME%" >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO War copied.

:: Wait for tomcat to be ready
ping 127.0.0.1 -n %TIMEOUT% > nul


ECHO Calling %TOMCAT_HOST%%TOMCAT_SERVICE% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %TOMCAT_HOST%%TOMCAT_SERVICE% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %TOMCAT_HOST%%TOMCAT_SERVICE% > %INTEGRATION_HOME%\tomcatResponse
SET /P tomcatResponse=<%INTEGRATION_HOME%\tomcatResponse
DEL "%INTEGRATION_HOME%\tomcatResponse"

IF NOT ["%tomcatResponse%"] == ["{"testOk":true}"] (
	ECHO ERROR : Tomcat can't be reached >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : Tomcat can't be reached
	GOTO :end
)
ECHO Service Avalaible
ECHO Service Avalaible >> %INTEGRATION_HOME%\%LOG_FILE%

IF ["%ACTIVEMQ_HOME%"] == [""] (
	ECHO ERROR : ACTIVEMQ_HOME is not defined >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : ACTIVEMQ_HOME is not defined
	GOTO :end
)

ECHO Starting ActiveMQ ...
ECHO /---------- Starting ActiveMQ -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%
START "" "%ACTIVEMQ_HOME%\bin\activemq.bat" start  >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%


:: Wait for activeMQ to be ready
ping 127.0.0.1 -n %TIMEOUT% > nul


ECHO Calling %ACTIVEMQ_HOST% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %ACTIVEMQ_HOST% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %ACTIVEMQ_HOST% > %INTEGRATION_HOME%\activeMQResponse

setlocal ENABLEDELAYEDEXPANSION
set vidx=0
for /F "tokens=*" %%A in (%INTEGRATION_HOME%\activeMQResponse) do (
    SET /A vidx=!vidx! + 1 > nul
    set var!vidx!=%%A > nul
)
set var > nul

IF NOT ["%var1%"] == [""] (
    ECHO COUCOU
)

::IF ["%activeMQResponse%"] == [""] (
::	ECHO ERROR : ActiveMQ can't be reached >> %INTEGRATION_HOME%\%LOG_FILE%
::	ECHO ERROR : ActiveMQ can't be reached
::	GOTO :end
::)
DEL "%INTEGRATION_HOME%\activeMQResponse"

:end
CD %INTEGRATION_HOME%

:: CLOSE EVERYTHING