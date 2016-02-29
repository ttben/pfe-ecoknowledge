ECHO Stopping ActiveMQ ...
ECHO /---------- Stopping ActiveMQ -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%
START "" "%ACTIVEMQ_HOME%\bin\activemq.bat" stop  >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%