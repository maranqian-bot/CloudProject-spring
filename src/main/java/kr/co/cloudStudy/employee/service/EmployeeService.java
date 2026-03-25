package kr.co.cloudStudy.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    /**
     * [실제 조회] DB에서 검색 조건과 페이징에 맞춰 데이터 가져오기
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResDto> searchEmployees(EmployeeSearchDto condition, Pageable pageable) {
        // 1. 리포지토리(Impl)에서 엔티티 페이지를 가져옴
        Page<EmployeeEntity> entityPage = employeeRepository.searchEmployees(condition, pageable);
        
        // 2. 엔티티를 ResDto로 변환
        return entityPage.map(EmployeeResDto::fromEntity);
    }

    /**
     * [테스트용] 화면 확인을 위한 더미 데이터
     * 주의: DTO 필드 타입이 숫자형으로 변경되었으므로 값도 숫자로 넣어야 합니다.
     */
    public Page<EmployeeResDto> getEmployeeList(Pageable pageable) {
        List<EmployeeResDto> dummyList = List.of(
            EmployeeResDto.builder().id(70224L).name("Julianne Sterling").deptId(1L).position("매니저").email("j.sterling@arch.com").status(0).build(),
            EmployeeResDto.builder().id(70225L).name("Marcus Kael").deptId(2L).position("관리자").email("m.kael@arch.com").status(1).build(),
            EmployeeResDto.builder().id(70226L).name("Lana Tesh").deptId(3L).position("팀장").email("l.tesh@arch.com").status(2).build(),
            EmployeeResDto.builder().id(70234L).name("Dorian Vance").deptId(4L).position("직원").email("d.vance@arch.com").status(0).build(),
            EmployeeResDto.builder().id(70241L).name("Reese Blackwood").deptId(5L).position("매니저").email("r.blackwood@arch.com").status(0).build()
        );
        
        // 3. 인자 설명: (조회 목록, 페이징 정보, 전체 개수)
        return new PageImpl<>(dummyList, pageable, 1248L); 
    } 
} 