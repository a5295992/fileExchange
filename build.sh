
mvn spring-boot:build-image -f pom.xml

rm -rf ./target/hbaseSearch-0.0.1-SNAPSHOT.jar

mvn jar:jar -f pom.xml