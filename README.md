# grpc-03-udemy
Implementing a GRPC Streaming client

1) PROTO PLUGINS 
   https://github.com/grpc/grpc-java
   
2) The code generator didn't work as expected as a maven pom project so..everytime I want to debug | execute the server |client I 
   need to comment the plugin in pom.xml
   For generating stubs, uncomment the pom plugin pieace and run ```mvn clean install -U``` by command line
