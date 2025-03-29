@echo off
:: Setze den Pfad zu deinem JAR und den Lib-Ordner

set LIB_PATH=lib\*

:: Starte das Java-Programm mit den richtigen Optionen
java --module-path lib/lib --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.web -cp "%JAR_PATH%;%LIB_PATH%" -jar Fubar.jar

pause