@echo off
set JDK_HOME=c:\PROGRA~1\Java\jdk1.8.0_181
@echo "Compiling code ..."
@mkdir bin
%JDK_HOME%/bin/javac.exe -d bin -cp src src/main/java/Apples2Apples.java
@echo "Compiling tests ..."
set JUNIT=lib\org.junit4-4.3.1.jar
%JDK_HOME%/bin/javac.exe -d bin -cp %JUNIT%;src src/main/test/TestRunner.java