# MyBatis Config
## parent pom에 다음 의존성 확인
1. mybatis 의존성   
```xml   
	<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
		<version>2.1.1</version>
	</dependency>
```   
2. postgresql 의존성   
```xml   
<dependency>
	<groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
</dependency>
```   
## application properties에 datasource관련 property 추가
local, dev, stage, prod 각각의 property 파일(application-{profile}.yml)에 
다음을 추가한다.   
```   
spring:
  datasource:
    data-source-class-name: org.postgresql.ds.PGSimpleDataSource
    hikari:
      jdbc-url: "jdbc:postgresql://{IP}:{port}/{dbname}?charSet=UTF-8"
      username: {user}
      password: {password}
```   
## MybatisConfig 클래스 제작   
**com.fkl.mbrs.config.MybatisConfig**   
## Mybatis config 파일 추가   
mybatis-config.xml에 MyBatis의 설정을 추가할 수 있다.   
다음과 같이 클래스 변수와 테이블 컬럼을 매핑하는 설정을 추가한다.   
```xml
<configuration>
	<settings>
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>
</configuration>
```   
## Mapper xml 추가      
https://mybatis.org/mybatis-3/ko/configuration.html 참고   
## Mapper 인터페이스 추가   



