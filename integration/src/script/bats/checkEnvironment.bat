
IF ["%CATALINA_HOME%"] == [""] (
	ECHO ERROR : CATALINA_HOME is not defined >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : CATALINA_HOME is not defined
	EXIT 1
)

IF ["%CURL_HOME%"] == [""] (
    ECHO ERROR : CURL_HOME is not defined >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : CURL_HOME is not defined
	EXIT 1
)

IF ["%ACTIVEMQ_HOME%"] == [""] (
	ECHO ERROR : ACTIVEMQ_HOME is not defined >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : ACTIVEMQ_HOME is not defined
	EXIT 1
)