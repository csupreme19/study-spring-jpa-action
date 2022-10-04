package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.MemberDuplicateException;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    // 기본 트랜잭션을 readOnly로 두고 커밋이 필요한 트랜잭션만 선언
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        if(memberRepository.countByName(member.getName()) > 0) {
            throw new MemberDuplicateException();
        }
    }

    // 회원 전체 검색
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단일 검색
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
