# sbt-crossbuild-broken

try these commands:

working (using v 1.3.0)
```bash
docker build -f WorkingVersionDockerfile --tag sbt-broken-build .
```


working (using v 1.3.4)
```bash
docker build -f BrokenVersionDockerfile --tag sbt-working-build .
```

## Output of docker-build

### Version 1.3.4: `sbt "+update" broken`
```
Step 15/16 : RUN sbt "+update"
 ---> Running in 4c9eb5f75f7b
Copying runtime jar.
Removing intermediate container 4c9eb5f75f7b
 ---> 221bdae10f88
```

### Version 1.3.0: `sbt "+update" working`
```
Step 15/16 : RUN sbt "+update"
 ---> Running in 9adc02c4ec89
OpenJDK 64-Bit Server VM warning: Ignoring option MaxPermSize; support was removed in 8.0
Copying runtime jar.
OpenJDK 64-Bit Server VM warning: Ignoring option MaxPermSize; support was removed in 8.0
OpenJDK 64-Bit Server VM warning: Ignoring option MaxPermSize; support was removed in 8.0
[info] [launcher] getting org.scala-sbt sbt 1.3.4  (this may take some time)...
downloading https://repo1.maven.org/maven2/org/scala-sbt/run_2.12/1.3.4/run_2.12-1.3.4.jar ...
downloading https://repo1.maven.org/maven2/org/scala-sbt/main-settings_2.12/1.3.4/main-settings_2.12-1.3.4.jar ...
downloading https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.10/scala-library-2.12.10.jar ...
... 
sbt doing it's thing to resolve everything
```