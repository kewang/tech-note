主要是要介紹Android如何使用Maven來build app

1. 安裝最新版的Android SDK
2. 安裝[Maven Android SDK Deployer](https://github.com/mosabua/maven-android-sdk-deployer)
3. 調整Android專案，新增pom.xml
4. 執行`mvn clean install android:deploy android:run`在手機上執行成功

## Maven Android SDK Deployer

* 將已經下載的Android SDK擺到maven的repository位置，也就是`~/.m2/repository`
* 執行`mvn install`就可以將Android SDK擺到maven的repository了。
* 注意要用maven 3.1.1以上才行
* 注意要`export ANDROID_HOME=android-path`才行

## pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>padfone-launcher-switcher</groupId>
	<artifactId>padfone-launcher-switcher</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>apk</packaging>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>
	<dependencies>
		<dependency>
			<groupId>android</groupId>
			<artifactId>android</artifactId>
			<version>4.0_r3</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>android.support</groupId>
			<artifactId>compatibility-v4</artifactId>
			<version>19.0.1</version>
		</dependency>
	</dependencies>
	<build>
		<sourceDirectory>src</sourceDirectory>
		<finalName>${project.artifactId}</finalName>
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>com.jayway.maven.plugins.android.generation2</groupId>
					<artifactId>android-maven-plugin</artifactId>
					<version>3.8.0</version>
					<extensions>true</extensions>
				</plugin>
			</plugins>
		</pluginManagement>
		<plugins>
			<plugin>
				<groupId>com.jayway.maven.plugins.android.generation2</groupId>
				<artifactId>android-maven-plugin</artifactId>
				<configuration>
					<genDirectory>${project.basedir}/gen</genDirectory>
					<sdk>
						<platform>14</platform>
					</sdk>
				</configuration>
			</plugin>
			<plugin>
				<artifactId>maven-clean-plugin</artifactId>
				<version>2.5</version>
				<configuration>
					<filesets>
						<fileset>
							<directory>gen/</directory>
						</fileset>
					</filesets>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
```

### dependency的Android版本要選擇正確

[version要跟deployer一樣](https://github.com/mosabua/maven-android-sdk-deployer)

```xml
<dependency>
	<groupId>android</groupId>
	<artifactId>android</artifactId>
	<version>4.0_r3</version>
	<scope>provided</scope>
</dependency>
```

### android-maven-plugin的名稱別打錯

[android-maven-plugin](http://jayway.github.io/maven-android-plugin/)在以前叫做maven-android-plugin，要注意別打錯

```xml
<pluginManagement>
	<plugins>
		<plugin>
			<groupId>com.jayway.maven.plugins.android.generation2</groupId>
			<artifactId>android-maven-plugin</artifactId>
			<version>3.8.0</version>
			<extensions>true</extensions>
		</plugin>
	</plugins>
</pluginManagement>
<plugins>
	<plugin>
		<groupId>com.jayway.maven.plugins.android.generation2</groupId>
		<artifactId>android-maven-plugin</artifactId>
		<configuration>
			<genDirectory>${project.basedir}/gen</genDirectory>
			<sdk>
				<platform>14</platform>
			</sdk>
		</configuration>
	</plugin>
</plugins>
```

### `<packaging/>`的值要正確

一般都用`jar`，可是這邊要用`apk`，否則沒辦法build成功(R.java會無法產生，因為不知道使用的是Android Compiler)

### R.java無法產生

Eclipse開發時必須動態產生R.java，但是R.java預設是產生在`target/generate-sources/r`這個資料夾下面，所以要加上`<genDirectory>${project.basedir}/gen</genDirectory>`，這樣子就會產生跟Eclipse一樣的目錄`gen`了。

### mvn clean的刪除問題

因為產生gen/資料夾的關係，`mvn clean`無法清除gen資料夾，所以要調整maven-clean-plugin的參數：

```xml
<plugin>
	<artifactId>maven-clean-plugin</artifactId>
	<version>2.5</version>
	<configuration>
		<filesets>
			<fileset>
				<directory>gen/</directory>
			</fileset>
		</filesets>
	</configuration>
</plugin>
```

## deploy到sonatype的repository

https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide

**TODO**

# 參考連結

* [Android build system workflow](http://tools.android.com/tech-docs/new-build-system/build-workflow)
* [Android Application Development with Maven](http://books.sonatype.com/mvnref-book/reference/android-dev.html)
* [Gradle Plugin User Guide](http://tools.android.com/tech-docs/new-build-system/user-guide)
