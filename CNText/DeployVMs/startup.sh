#!/bin/sh
cd /home/G01-LI61N/final
nohup java -cp Utils-1.0.jar:VMOCR-1.0-SNAPSHOT-jar-with-dependencies.jar vmocr.Worker -premium >> log.txt