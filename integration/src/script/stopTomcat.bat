ECHO Stopping Tomcat ...
ECHO /---------- Stopping Tomcat -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP%
CALL "%CATALINA_HOME%\bin\shutdown.bat" >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_STOP%