pushd c:\users\shiv\documents\orbit-skunkworks-master
call mvn clean package
@rem call mvn package
pushd single-launcher\target
call java -jar orbit-skunkworks-single-launcher-0.1.0-SNAPSHOT-jar-with-dependencies.jar
@rem popd

