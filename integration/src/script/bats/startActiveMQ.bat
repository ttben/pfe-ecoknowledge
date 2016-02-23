ECHO Starting ActiveMQ ...
ECHO /---------- Starting ActiveMQ -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%
START "" "%ACTIVEMQ_HOME%\bin\activemq.bat" start  >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_ACTIVEMQ%