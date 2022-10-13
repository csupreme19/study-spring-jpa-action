package jpabook.jpashop.service;

import jpabook.jpashop.domain.AccessLog;
import jpabook.jpashop.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;

    public Long log(AccessLog accessLog) {
        return accessLogRepository.save(accessLog);
    }

}
