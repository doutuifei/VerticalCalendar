# 自定义日历选择

[![](https://jitpack.io/v/mzyq/VerticalCalendar.svg)](https://jitpack.io/#mzyq/VerticalCalendar)

## 效果图

![img](https://github.com/mzyq/VerticalCalendar/blob/master/img/preview.gif)

[APK下载](https://w-5.net/YPqDk)

## 功能
* 连续选择
* 设置不可选项
* 自动调换开始和结束位置
* 回调和get获取选择的日期信息

## 使用

### Gradle
```
allprojects {
    repositories {
		...
		maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
        compile 'com.github.mzyq:VerticalCalendar:0.1.0'
    }
```

### Maven
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```
<dependency>
    <groupId>com.github.mzyq</groupId>
    <artifactId>VerticalCalendar</artifactId>
    <version>0.1.0</version>
</dependency>
```
