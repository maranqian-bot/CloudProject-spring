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

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    
    private final EntityManager em;

    @Override
    public Page<EmployeeEntity> searchEmployees(EmployeeSearchDto condition, Pageable pageable) {
        // 1. 쿼리 문장 조립
        StringBuilder jpql = new StringBuilder("SELECT e FROM EmployeeEntity e WHERE 1=1");
        
        // 키워드 검색 (CAST 사용 - 사번 id 필드 기준)
        if (condition.getKeyword() != null && !condition.getKeyword().isEmpty()) {
            jpql.append(" AND (e.name LIKE :keyword OR CAST(e.id AS string) LIKE :keyword)");
        }
        
        // [수정] ERD에 부서명 필드가 없으므로, 일단 deptId(Long) 기준으로 검색하도록 변경
        // 만약 DTO에서 부서명을 받는다면, 이 부분은 추후 Join 쿼리로 확장해야 합니다.
        if (condition.getDepartment() != null && !condition.getDepartment().isEmpty()) {
            jpql.append(" AND CAST(e.deptId AS string) = :deptId");
        }
        
        // [수정] status가 Integer(0:재직, 1:휴직, 2:퇴사)이므로 숫자 비교로 변경
        if (condition.getStatus() != null && !condition.getStatus().isEmpty()) {
            jpql.append(" AND e.status = :status");
        }
        
        // 2. 쿼리 생성
        TypedQuery<EmployeeEntity> query = em.createQuery(jpql.toString(), EmployeeEntity.class);
        
        // 3. 파라미터 바인딩
        setParameters(query, condition);
        
        // 4. 페이징 적용
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<EmployeeEntity> resultList = query.getResultList();
        
        // 5. 카운트 쿼리
        String countJpql = jpql.toString().replace("SELECT e", "SELECT count(e)");
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        
        setParameters(countQuery, condition);
        
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, total != null ? total : 0L);
    }

    private void setParameters(TypedQuery<?> query, EmployeeSearchDto condition) {
        if (condition.getKeyword() != null && !condition.getKeyword().isEmpty()) {
            query.setParameter("keyword", "%" + condition.getKeyword() + "%");
        }
        if (condition.getDepartment() != null && !condition.getDepartment().isEmpty()) {
            query.setParameter("deptId", condition.getDepartment()); // 부서 ID값 전달
        }
        if (condition.getStatus() != null && !condition.getStatus().isEmpty()) {
            // DTO에서 문자열로 넘어온 상태값을 숫자로 변환하여 바인딩
            query.setParameter("status", Integer.parseInt(condition.getStatus()));
        }
    }
}