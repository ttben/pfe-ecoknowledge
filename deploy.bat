:: Compile source code
call mvn install

:: Deploy frontend server
call xcopy /F /Y .\server\target\Ecoknowledge.war "C:\Program Files (x86)\apache-tomcat-8.0.29\webapps\"

:: Deploy fakeDataSource
call xcopy /F /Y .\fakeDataSource\target\fakeDataSource.war "C:\Program Files (x86)\apache-tomcat-8.0.29\webapps\"

:: Deploy webTierFeeder
call xcopy /F /Y .\feeder\webTierFeeder\target\webTierFeeder.war "C:\Program Files (x86)\apache-tomcat-8.0.29\webapps\"

:: Start mongodb
start cmd.exe /C mongod

:: Start activemq
start cmd.exe /C "C:\Program Files (x86)\apache-activemq-5.13.0\bin\win64\activemq.bat"

:: Start tomcat
start cmd.exe /C "C:\Program Files (x86)\apache-tomcat-8.0.29\bin\startup.bat"

