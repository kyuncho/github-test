회원 서비스      
# 부모의존성 설정   
pom.xml에 parent를 설정한다.   
```xml
	<parent>
		<groupId>com.fkl</groupId>
		<artifactId>fkl-common-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
```   
[공통 의존성 파일](http://10.166.94.116:8089/fklse/fkl-common-parent)   
# 스프링 부트 애플리케이션 실행 플러그인 설정   
메이븐으로 스프링 부트 애플리케이션을 실행하도록 build plugin에 spring-boot-maven-plugin이 
추가되어 있는지 확인하고 없으면 추가한다.   
```xml
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
```   
# 스프링부트 프로퍼티   
스프링부트가 로딩될 때에 필요한 설정들을 자동으로 로딩하기 위해 외부 파일에 프로퍼티를 설정하며   
*.properties 또는 *.yml 파일을 사용한다. 본 프로젝트에서는 *.yml 파일을 사용하고, config 디렉토리에
파일을 저장한다. 프로퍼티는 local, dev, stg, prd로 프로파일을 구분하여 관리한다. 
파일명 형식은 application[-profile].yml이다.   
# Postman 설치
REST API는 MVC Controller와는 달리 ResponseBody에 데이터와 응답코드를 반환하므로 기본적으로 
화면없이 테스트를 진행한다. API 테스를 위해 [Postman](https://www.postman.com/downloads/)을 
다운로드하여 설치한다. 
# MyBatis Configuration 추가   
[Java Config](documents/README-configure.md)   
# JUnit 테스트   
## 스프링 부트 테스트   
JUnit5로 스프링 부트 테스트를 하기 위해서는 Test 클래스의 클래스 선언에 '@SpringBootTest'를 기술한다.   
@SpringBootTest 어노테이션은 스프링부트 애플리케이션 테스트에 필요한 거의 모든 의존성을 제공한다.   
테스트 클래스 선언부에 @SpringBootTest를 기술한다.   
WEB MVC Controller를 테스트하기 위해 @AutoConfigureMockMvc를 추가한다.      
```java   
@SpringBootTest
@AutoConfigureMockMvc
public class MberControllerTest {
...
}
```    
MocMvc와 ObjectMapper를 자동주입한다.   
ObjectMapper는 API 호출시 전달하는 JSON 파라미터를 쉽게 만들기 위해 사용한다.      
```java   
public class MberControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
...
}
```   
테스트 메소드를 만든다.   
```java   
public class MberControllerTest {
...
	@Test
	public void createMberTest() throws Exception {
				
		MberDto mberDto = new MberDto().builder()
							.korMberNm("홍길동")
							.loginId("loginID1")
							.loginPasswd("12345678")
							.firstRegId("loginID")
							.firstRegProgmId("createMber")
							.lastChngId("loginID")
							.lastChngProgmId("createMber")
							.build();
		
		mockMvc.perform(post("/api/mber/v1.0.0/mber")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mberDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.retKey").isNotEmpty())
		.andDo(print());
		
	}
...
}
```   
* perform : DispatcherServlet에 요청을 의뢰한다. 결과로 ResultAction 객체를 받는다. ResultAction 
객체는 리턴 값을 검증하고 확인할 수 있는 andExpect() 메소드를 제공한다.   
* accept : 응답 데이터의 타입(MIME)을 설정한다.      
* contentType : 전송하는 데이터의 컨텐츠 타입과 문자열 인코딩을 설정한다.   
* content : 전송하는 데이터를 설정한다.   
* andExpect : 응답을 검증한다.      
  + 상태 코드(status())   
    - 메소드 이름 : 상태 코드   
    - isOk() : 200   
    - isNotFound() : 404   
    - isMethodNotAllowd() : 405   
    - isInternalServerError() : 500   
    - is(int status) : status 상태 코드   
  + 뷰(view())   
    - 리턴하는 뷰 이름을 검증한다.   
    - ex. view().name("/login") : 리턴하는 뷰 이름이 '/login'인가?      
  + 리다이렉트(redirect())   
    - 리다이렉트 응답을 검증한다.   
    - ex. redirectUrl("/login") : '/login'으로 리다이렉트되었는가?   
  + 모델 정보(model())   
    - 컨트롤러에서 지정한 모델들의 정보 검증   
  + 응답 정보 검증(content())   
    - 응답에 대한 정보를 검증한다.      
  + json 검증(jsonPath())   
    - 응답을 JSON을 받을 때에 각 항목을 검증한다.   
    - jsonPath dependency를 추가해야 한다.   




