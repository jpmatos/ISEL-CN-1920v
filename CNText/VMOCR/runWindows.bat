@echo off
set GOOGLE_APPLICATION_CREDENTIALS=C:\Users\helio.fitas\Dropbox\Helio\ISEL\2019-2020 SV\CN\LABs\Lab6\myKey\g01-li61n-39ec5d38397d.json
java -cp ../lib/Utils-1.0.jar;target/VMOCR-1.0-SNAPSHOT-jar-with-dependencies.jar vmocr.Worker -free
