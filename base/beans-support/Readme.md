## saatsch.framework.base.beans-support

pulls in [joda-beans](http://www.joda.org/joda-beans/).

Projects that are using beans-support and want to define own beans must also declare:
	
	<plugin>
		<groupId>org.joda</groupId>
		<artifactId>joda-beans-maven-plugin</artifactId>
		<version>1.0</version>
		<executions>
			<execution>
				<id>joda-beans-generate</id>
				<goals>
					<goal>generate</goal>
				</goals>
			</execution>
		</executions>
	</plugin>
	

beans-support is required for jmmo.core.data 