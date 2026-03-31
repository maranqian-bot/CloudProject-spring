package kr.co.cloudStudy.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
	private final EmployeeRepository employeeRepository;
	// 메인화면의 전체 직원 목록 조회 (entity -> ResDTO로 변환하엿음 fromEntity메서드로 )
	
//	public List<EmployeeResDTO> getEmployeeList()  { //리스트형태로 응답DTO쪽 보여주는 메서드
//		//리스트 필드 employees <- 레포에서 조회 메서드 선택함
//		List<EmployeeResDTO> employees = employeeRepository.findAll().stream()
//				.map(EmployeeResDTO:: fromEntity)
//				.toList();
//	return employees;
//}
	// 테스트용 
public Page<EmployeeResDto> getEmployeeList(Pageable pageable) {
        
        // 1. 실제 DB 대신 사용할 5명의 더미 리스트
        List<EmployeeResDto> dummyList = List.of(
            EmployeeResDto.builder().employeeId(70224L).name("Julianne Sterling").deptName("전략 및 기획").position("매니저").email("j.sterling@arch.com").status("활성").build(),
            EmployeeResDto.builder().employeeId(70225L).name("Marcus Kael").deptName("구조 엔지니어링").position("관리자").email("m.kael@arch.com").status("휴가").build(),
            EmployeeResDto.builder().employeeId(70226L).name("Lana Tesh").deptName("인테리어 디자인").position("팀장").email("l.tesh@arch.com").status("외근").build(),
            EmployeeResDto.builder().employeeId(70234L).name("Dorian Vance").deptName("기업 재무").position("직원").email("d.vance@arch.com").status("활성").build(),
            EmployeeResDto.builder().employeeId(70241L).name("Reese Blackwood").deptName("시스템 엔지니어링").position("매니저").email("r.blackwood@arch.com").status("활성").build()
        );

        // 2. 리스트를 Page 객체로 변환 (리액트가 인식할 수 있는 규격)
        // 인자 설명: (현재 페이지에 보여줄 목록, 리액트가 보낸 페이징 정보, 전체 데이터 개수)
        return new PageImpl<>(dummyList, pageable, 1248); 
    }
}
