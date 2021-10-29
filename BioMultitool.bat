@echo off 
javac *.java
set classpath=%classpath%;
java Driver.java
pause