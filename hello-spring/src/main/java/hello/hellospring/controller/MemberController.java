package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//controller 외부 요청 받기
//service 로직 만들기
//repository 데이터 저장

@Controller
public class MemberController {

    //컨테이너에는 하나만 등록된다.
    private final MemberService memberService;

    @Autowired //컨트롤러가 생성이 될때 멤버 서비스 객체를 연곃 / 의존성 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
