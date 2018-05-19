# Swift Maven Wagon
This project is a [Maven Wagon][wagon] for [OpenStack Swift][swift].  In order to to publish artifacts to an swift container, the user (as identified by their access key) must be listed as an owner on the container.

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
      <id>swift-release</id>
      <name>Swift Release Repository</name>
      <url>swift://<host>/release</url>
    </repository>
    <snapshotRepository>
      <id>swift-snapshot</id>
      <name>Swift Snapshot Repository</name>
      <url>swift://<host>/snapshot</url>
    </snapshotRepository>
  </distributionManagement>
  ...
</project>
```

Finally the `~/.m2/settings.xml` must be updated to include access and secret keys for the account. The access key should be used to populate the `username` element, and the secret access key should be used to populate the `password` element.

```xml
<settings>
  ...
  <servers>
    ...
    <server>
      <id>swift-release</id>
      <username>0123456789ABCDEFGHIJ</username>
      <password>0123456789abcdefghijklmnopqrstuvwxyzABCD</password>
    </server>
    <server>
      <id>swift-snapshot</id>
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