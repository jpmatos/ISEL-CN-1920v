#!/bin/sh
cd /home/G01-LI61N/final
nohup java -cp Utils-1.1.jar:Translation-1.0-SNAPSHOT-jar-with-dependencies.jar vmTranslate.Worker -premium >> log.txt