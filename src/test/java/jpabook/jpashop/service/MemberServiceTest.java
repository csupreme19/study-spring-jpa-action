package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.MemberDuplicateException;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @PersistenceContext
    EntityManager em;

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
    public void 회원목록조회_실패() throws Exception {
        assertThrows(IllegalStateException.class, () -> memberService.findMembers());
    }

    @Test
    public void 회원목록조회_성공() throws Exception {
        // given
        Member member = Member.builder()
                .name("kim")
                .build();
        em.persist(member);

        // when
        List<Member> members = memberService.findMembers();

        // then
        assertThat(members.size()).isNotZero();
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