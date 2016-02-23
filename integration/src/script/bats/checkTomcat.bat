ECHO Calling %TOMCAT_HOST%%TOMCAT_SERVICE% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %TOMCAT_HOST%%TOMCAT_SERVICE% >> %INTEGRATION_HOME%\%LOG_FILE%
CALL "%CURL_HOME%\curl.exe" -get %TOMCAT_HOST%%TOMCAT_SERVICE% > %INTEGRATION_HOME%\tomcatResponse
SET /P tomcatResponse=<%INTEGRATION_HOME%\tomcatResponse
DEL "%INTEGRATION_HOME%\tomcatResponse"

IF NOT ["%tomcatResponse%"] == ["{"testOk":true}"] (
	ECHO ERROR : Tomcat can't be reached >> %INTEGRATION_HOME%\%LOG_FILE%
	ECHO ERROR : Tomcat can't be reached
	EXIT 1
)

ECHO Tomcat Avalaible
ECHO Tomcat Avalaible >> %INTEGRATION_HOME%\%LOG_FILE%