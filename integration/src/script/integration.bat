CLS
@ECHO OFF

SET "INTEGRATION_HOME=%CD%"
ECHO INTEGRATION_HOME : %INTEGRATION_HOME% > %INTEGRATION_HOME%\%LOG_FILE%

CALL %INTEGRATION_HOME%\configurations.bat
CALL %INTEGRATION_HOME%\checkEnvironment.bat
CALL %INTEGRATION_HOME%\stopTomcat.bat
CALL %INTEGRATION_HOME%\startTomcat.bat

CD %SOURCE_MODULE_RELATIVE_PATH%

CALL %INTEGRATION_HOME%\mvnBuild.bat
CALL %INTEGRATION_HOME%\cpyWar.bat

CD %INTEGRATION_HOME%

CALL %INTEGRATION_HOME%\waitTomcat.bat
CALL %INTEGRATION_HOME%\checkTomcat.bat

CALL %INTEGRATION_HOME%\startActiveMQ.bat

CALL %INTEGRATION_HOME%\waitActiveMQ.bat
::CALL %INTEGRATION_HOME%\checkActiveMQ.bat

ECHO Calling %ACTIVEMQ_HOST% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %ACTIVEMQ_HOST% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %ACTIVEMQ_HOST% > %INTEGRATION_HOME%\activeMQResponse

::TYPE %INTEGRATION_HOME%\activeMQResponse

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
::DEL "%INTEGRATION_HOME%\activeMQResponse"

CALL %INTEGRATION_HOME%\stopActiveMQ.bat
CALL %INTEGRATION_HOME%\stopTomcat.bat


:: CLOSE EVERYTHING