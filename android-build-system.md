主要是要介紹Android如何使用Maven以及Gradle來build app

# 先從Maven開始介紹

1. 安裝最新版的Android SDK
2. 安裝[Maven Android SDK Deployer](https://github.com/mosabua/maven-android-sdk-deployer)
3. 調整Android專案，新增pom.xml
4. 執行`mvn clean install android:deploy android:run`在手機上執行成功

## Maven Android SDK Deployer

* 看起來是將已經下載的Android SDK擺到maven的repository位置，也就是`~/.m2/repository`
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
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.4</version>
		</dependency>
		<dependency>
			<groupId>com.firebase</groupId>
			<artifactId>firebase-client</artifactId>
			<version>1.0.11</version>
		</dependency>
		<dependency>
			<groupId>org.jsoup</groupId>
			<artifactId>jsoup</artifactId>
			<version>1.7.3</version>
		</dependency>
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.2.4</version>
		</dependency>
		<dependency>
			<groupId>com.squareup.picasso</groupId>
			<artifactId>picasso</artifactId>
			<version>2.1.1</version>
		</dependency>
		<dependency>
			<groupId>com.j256.ormlite</groupId>
			<artifactId>ormlite-core</artifactId>
			<version>4.47</version>
		</dependency>
		<dependency>
			<groupId>com.j256.ormlite</groupId>
			<artifactId>ormlite-android</artifactId>
			<version>4.47</version>
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
					<sdk>
						<platform>14</platform>
					</sdk>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
```

* dependency的Android版本要[選擇正確(跟deployer一樣)](https://github.com/mosabua/maven-android-sdk-deployer)
```xml
<dependency>
	<groupId>android</groupId>
	<artifactId>android</artifactId>
	<version>4.0_r3</version>
	<scope>provided</scope>
</dependency>
```

* [android-maven-plugin](https://code.google.com/p/maven-android-plugin/)在以前叫做maven-android-plugin，要注意別打錯。
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
			<sdk>
				<platform>14</platform>
			</sdk>
		</configuration>
	</plugin>
</plugins>
```

* 記得`<packaging>apk</packaging>`要用apk，否則沒辦法build成功(R.java會無法產生)

* R.java的產生問題：

Eclipse開發時必須動態產生R.java，但是R.java預設是產生在`target/generate-sources/r`這個資料夾下面，所以要加上`<genDirectory>${project.basedir}/gen</genDirectory>`，這樣子就會產生跟Eclipse一樣的目錄`gen`了。

* mvn clean的刪除問題：

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

# 參考連結

* [Android build system workflow](http://tools.android.com/tech-docs/new-build-system/build-workflow)
* [Android Application Development with Maven](http://books.sonatype.com/mvnref-book/reference/android-dev.html)
* [Gradle Plugin User Guide](http://tools.android.com/tech-docs/new-build-system/user-guide)