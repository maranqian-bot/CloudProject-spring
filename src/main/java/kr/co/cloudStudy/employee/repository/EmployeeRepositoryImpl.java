package kr.co.cloudStudy.employee.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    
    private final EntityManager em;

    @Override
    public Page<EmployeeEntity> searchEmployees(EmployeeSearchDto condition, Pageable pageable) {
        // 1. 기본 쿼리 생성
        StringBuilder jpql = new StringBuilder("SELECT e FROM EmployeeEntity e WHERE 1=1");
        
        // 2. 동적 조건 추가 (StringUtils로 공백/null 체크)
        if (StringUtils.hasText(condition.getKeyword())) {
            // 이름(name) 필드에 키워드가 포함되는지 검색
            jpql.append(" AND e.name LIKE :keyword");
        }
        
        if (StringUtils.hasText(condition.getDepartment())) {
            // 부서(deptName 또는 deptId) 필드 매칭 (엔티티 필드명에 맞춰 수정 필요)
            jpql.append(" AND e.deptName = :department");
        }
        
        if (StringUtils.hasText(condition.getStatus())) {
            // 상태값(status) 비교
            jpql.append(" AND e.status = :status");
        }

        // 3. 메인 데이터 조회용 쿼리 생성
        TypedQuery<EmployeeEntity> query = em.createQuery(jpql.toString(), EmployeeEntity.class);
        
        // 4. 파라미터 바인딩
        setParameters(query, condition);
        
        // 5. 페이징 적용 (0부터 시작하는 offset 계산)
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<EmployeeEntity> resultList = query.getResultList();
        
        // 6. 전체 개수 조회용 카운트 쿼리 (페이징 처리에 필요)
        String countJpql = "SELECT COUNT(e) FROM EmployeeEntity e WHERE 1=1";
        if (StringUtils.hasText(condition.getKeyword())) countJpql += " AND e.name LIKE :keyword";
        if (StringUtils.hasText(condition.getDepartment())) countJpql += " AND e.deptName = :department";
        if (StringUtils.hasText(condition.getStatus())) countJpql += " AND e.status = :status";
        
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        setParameters(countQuery, condition);
        
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, total != null ? total : 0L);
    }

    // 파라미터 바인딩 공통 로직
    private void setParameters(TypedQuery<?> query, EmployeeSearchDto condition) {
        if (StringUtils.hasText(condition.getKeyword())) {
            query.setParameter("keyword", "%" + condition.getKeyword() + "%");
        }
        if (StringUtils.hasText(condition.getDepartment())) {
            query.setParameter("department", condition.getDepartment());
        }
        if (StringUtils.hasText(condition.getStatus())) {
            // DB 필드 타입이 Integer라면 Integer.parseInt() 사용
            query.setParameter("status", condition.getStatus());
        }
    }
}