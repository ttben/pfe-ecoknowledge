ECHO Starting Tomcat ...
ECHO /---------- Starting Tomcat -----------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START%
CALL "%CATALINA_HOME%\bin\startup.bat"  >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START% 2>>&1
ECHO /--------------------------------------/ >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START%
ECHO >> %INTEGRATION_HOME%\%LOG_FILE_TOMCAT_START%