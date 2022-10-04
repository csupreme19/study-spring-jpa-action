package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.MemberDuplicateException;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = Member.builder()
                .name("kim")
                .build();

        // when
        Long memberId = memberService.join(member);
        Member findMember = memberRepository.findById(memberId);

        // then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member = Member.builder()
                .name("kim")
                .build();
        Member joinMember = Member.builder()
                .name("kim")
                .build();

        // when
        memberService.join(member);

        // then
        assertThrows(MemberDuplicateException.class, () -> memberService.join(joinMember));
    }

}