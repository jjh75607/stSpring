package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링 컨테이너와 테스트를 함꼐 실행
@Transactional //테스트 실행 -> 트랜젝션 실행 -> 테스트 -> 롤백 (테스트에 있을때만)
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Qualifier("springDataJpaMemberRepository")
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Commit - 롤백 안 되고 커밋되는것
    void 회원가입() {
        //given - 주어진 상황 / 데이터
        Member member = new Member();
        member.setName("spring");

        //when - 실행 / 검증
        Long saveId = memberService.join(member);

        //then - 결과
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//뒤의 로직을 실행할때 앞에 에러가 터져야 함

        assertThat(e.getMessage()).isEqualTo("이미 존재 하는 회원");
    }
}