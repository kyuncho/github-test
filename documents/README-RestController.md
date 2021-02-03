#Rest Controller   
## Controller 생성
com.fkl.mber.controller 패키지를 생성하고 그 아래에 MberController 클래스를 생성한다.   
RestController로 동작하도록 @RestController를 클래스 선언 위에 등록한다.
@RestController는 View가 필요없는 REST 방식에서 사용되고 @ResponseBody를 포함하므로,
메소드마다 @ResponseBody를 붙여주지 않아도 된다.
@Controller는 View와 REST 방식을 동시에 사용할 수 있지만 @RestController는 REST방식만
사용할 수 있다.
간단히 hello 메소드를 만들어 정상 작동하는지 확인한다.
```java
@Slf4j
@RestController
@RequestMapping("/api/mber")
public class MberController {
	
	@GetMapping("/v1.0.0/hello")
	public String hello() {
		return "Hello!";
	}

}
```