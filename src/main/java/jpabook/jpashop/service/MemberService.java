package jpabook.jpashop.service;

import jpabook.jpashop.domain.AccessLog;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.MemberDuplicateException;
import jpabook.jpashop.model.AccessType;
import jpabook.jpashop.repository.AccessLogRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestScope;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final AccessLogService accessLogService;

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
        AccessLog accessLog = AccessLog.builder()
                .accessType(AccessType.READ)
                .apiCode("findMembers()")
                .build();
        accessLogService.log(accessLog);

        List<Member> members = memberRepository.findAll();
        if(members.isEmpty()) throw new IllegalStateException("회원이 존재하지 않습니다.");
        return members;
    }

    // 회원 단일 검색
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id);
        member.changeName(name);
    }
}
