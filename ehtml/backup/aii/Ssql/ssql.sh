#!/bin/sh

CLASSDIR=./libs
JDBCDIR=$CLASSDIR/jdbc

cd Ssql
# CLASSPATH=$CLASSPATH:$CLASSDIR/supersql.jar:$JDBCDIR/postgresql.jar:$JDBCDIR/sqlitejdbc-v056.jar:$CLASSDIR/json-simple-1.1.1.jar:$CLASSDIR/jsoup-1.7.2.jar:$CLASSDIR/servlet-api.jar:$CLASSDIR/log4j-1.2.17.jar:$CLASSDIR/commons-codec-1.10.jar java -Dfile.encoding=UTF-8 supersql.FrontEnd $*
CLASSPATH=$CLASSPATH:$CLASSDIR/supersql.jar:$JDBCDIR/postgresql.jar:$JDBCDIR/sqlitejdbc-v056.jar:$CLASSDIR/antlr-4.5-complete.jar:$CLASSDIR/commons-codec-1.10.jar:$CLASSDIR/commons-io-2.4.jar:$CLASSDIR/commons-lang3-3.4.jar:$CLASSDIR/hamcrest-core-1.3.jar:$CLASSDIR/jgrapht-core-0.9.1.jar:$CLASSDIR/jsass-5.1.1.jar:$CLASSDIR/json-simple-1.1.1.jar:$CLASSDIR/jsoup-1.7.2.jar:$CLASSDIR/junit-4.11.jar:$CLASSDIR/log4j-1.2.17.jar:$CLASSDIR/servlet-api.jar:$CLASSDIR/slf4j-api-1.7.21.jar:$CLASSDIR/slf4j-simple-1.7.21.jar:$CLASSDIR/REngine.jar:$CLASSDIR/JRI.jar:$CLASSDIR/JRIEngine.jar:$CLASSDIR/jsqlparser-1.2.jar java -Dfile.encoding=UTF-8 supersql.FrontEnd $*