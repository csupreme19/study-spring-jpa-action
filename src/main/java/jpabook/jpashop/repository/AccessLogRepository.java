package jpabook.jpashop.repository;

import jpabook.jpashop.domain.AccessLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AccessLogRepository {

    private final EntityManager em;

    public Long save(AccessLog accessLog) {
        if(accessLog.getId() == null) {
            em.persist(accessLog);
        } else {
            em.merge(accessLog);
        }
        return accessLog.getId();
    }

}
