參考連結：

* [Android build system workflow](http://tools.android.com/tech-docs/new-build-system/build-workflow)
* [Android Application Development with Maven](http://books.sonatype.com/mvnref-book/reference/android-dev.html)
* [Gradle Plugin User Guide](http://tools.android.com/tech-docs/new-build-system/user-guide)

先從Maven開始介紹：

1. 安裝最新版的Android SDK
2. 安裝[Maven Android SDK Deployer](https://github.com/mosabua/maven-android-sdk-deployer)
3. 調整Android專案，新增pom.xml
4. 執行`mvn clean install android:deploy android:run`在手機上執行成功

Maven Android SDK Deployer：

* 看起來是將已經下載的Android SDK擺到maven的repository位置，也就是`~/.m2/repository`
* 執行`mvn install`就可以將Android SDK擺到maven的repository了。
* 注意要用maven 3.1.1以上才行
* 注意要`export ANDROID_HOME=android-path`才行

[Maven pom.xml](https://gist.github.com/kewangtw/8456584)：

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