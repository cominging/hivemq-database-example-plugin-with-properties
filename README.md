# hivemq-database-example-plugin-with-properties

Add database.properties making the database configuration configurable once the plugin is deployed.
(modifying .jar file or compiling is not a simple job)

Forked from https://github.com/hivemq/hivemq-database-example-plugin

## Compiling

1. Install IntelliJ IDEA Community edition from https://www.jetbrains.com/idea/ 
2. Run IntelliJ IDEA. Use "Open project" to open pom.xml of this repository.
3. Open Maven Projects tab and build with "package".

Then you will get the jar file: "hivemq-database-example-plugin-3.0.1.jar"

## Installing

1. Setup your MySQL database and fill in corresponding properties in "database.properties"
2. Place "hivemq-database-example-plugin-3.0.1.jar" and "database.properties" in <HiveMQ installation directory>/plugins/
3. Then you may start HiveMQ

To test your configuration, you can use "mosquitto_pub.exe" (you can get from http://mosquitto.org/download/).
And use "mosquitto_pub.exe -h localhost -u test -P test -t 'test_topic' -m 'test_message' -d" to publish a simple message.
