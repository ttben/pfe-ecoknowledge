ECHO > %INTEGRATION_HOME%\%LOG_FILE%

ECHO Calling %ACTIVEMQ_HOST% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %ACTIVEMQ_HOST% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %ACTIVEMQ_HOST% > %INTEGRATION_HOME%\activeMQResponse

set "file=%INTEGRATION_HOME%\activeMQResponse"

CALL :checkempty "%file%"
goto :reached

:checkempty
if %~z1 == 0 goto :notreached
goto :reached

:notreached
ECHO ERROR : ActiveMQ can't be reached >> %INTEGRATION_HOME%\%LOG_FILE%
ECHO ERROR : ActiveMQ can't be reached
EXIT 1

:reached
ECHO ActiveMQ Avalaible
ECHO ActiveMQ Avalaible >> %INTEGRATION_HOME%\%LOG_FILE%

DEL "%INTEGRATION_HOME%\activeMQResponse" 2> nul