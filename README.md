# Swift Maven Wagon
This project is a [Maven Wagon][wagon] for [OpenStack Swift][swift].  In order to publish artifacts to an swift container, the user (as identified by their username and password) must have access to the container.

## Usage
To publish Maven artifacts to swift a build extension must be defined in a project's `pom.xml`.  The latest version of the wagon can be found on the [`swift-maven`][swift-maven] page in Maven Central.

```xml
<project>
  ...
  <build>
    ...
    <extensions>
      ...
      <extension>
        <groupId>com.aerse</groupId>
        <artifactId>swift-maven</artifactId>
        <version>1.0</version>
      </extension>
      ...
    </extensions>
    ...
  </build>
  ...
</project>
```

Once the build extension is configured distribution management repositories can be defined in the `pom.xml` with an `swift://` scheme.

```xml
<project>
  ...
  <distributionManagement>
    <repository>
      <id>container-name-snapshot</id>
      <url>swift://<host>/v3</url>
    </repository>
    <snapshotRepository>
      <id>container-name-release</id>
      <name><container name></name>
      <url>swift://<host>/v3</url>
    </snapshotRepository>
  </distributionManagement>
  ...
</project>
```

Finally the `~/.m2/settings.xml` must be updated to include username and password for the account.

```xml
<settings>
  ...
  <servers>
    ...
    <server>
      <id>container-name-release</id>
      <username>0123456789ABCDEFGHIJ</username>
      <password>0123456789abcdefghijklmnopqrstuvwxyzABCD</password>
    </server>
    <server>
      <id>container-name-snapshot</id>
      <username>0123456789ABCDEFGHIJ</username>
      <password>0123456789abcdefghijklmnopqrstuvwxyzABCD</password>
    </server>
    ...
  </servers>
  ...
</settings>
```

[swift-maven]: http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.aerse%22%20AND%20a%3A%22swift-maven%22
[swift]: https://docs.openstack.org/swift/latest/
[wagon]: http://maven.apache.org/wagon/