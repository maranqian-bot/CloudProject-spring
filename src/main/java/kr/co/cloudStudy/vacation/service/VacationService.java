package kr.co.cloudStudy.vacation.service;

import java.util.List;

import kr.co.cloudStudy.vacation.dto.MyVacationHistoryDTO;
import kr.co.cloudStudy.vacation.dto.PendingVacationApprovalDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestEmployeeResponseDTO;

public interface VacationService {

    /**
     * 특정 직원의 휴가 신청 이력 조회
     *
     * 해당 직원이 신청한 휴가 이력을 최신순으로 반환한다.
     *
     * @param employeeNumber 조회할 직원 사번
     * @return 휴가 신청 이력 DTO 리스트
     */
    List<MyVacationHistoryDTO> getMyVacationHistory(String employeeNumber);

    /**
     * 특정 승인자의 승인 대기 휴가 신청 목록 조회
     *
     * 해당 승인자가 처리해야 하는 승인 대기 휴가 신청 목록을 최신순으로 반환한다.
     *
     * @param approverId 승인자 직원 ID
     * @return 승인 대기 휴가 신청 DTO 리스트
     */
    List<PendingVacationApprovalDTO> getPendingApprovals(Long approverId);

    /**
     * 휴가 관리 페이지 전체 데이터 조회
     *
     * 상단 요약 정보, 내 휴가 신청 이력, 승인 대기 목록을 하나로 묶어 반환한다.
     *
     * @param employeeNumber 조회할 직원 사번
     * @param approverId 승인자 직원 ID
     * @param year 조회할 연도
     * @return 휴가 관리 페이지 응답 DTO
     */
    VacationManagementResponseDTO getVacationManagementPage(String employeeNumber, Long approverId, Integer year);

    /**
     * 휴가 신청 페이지 대상자 정보 조회
     *
     * 직원 기본 정보와 해당 연도의 사용 가능 연차를 반환한다.
     *
     * @param employeeNumber 조회할 직원 사번
     * @param year 조회할 연도
     * @return 휴가 신청 페이지 대상자 정보 DTO
     */
    VacationRequestEmployeeResponseDTO getVacationRequestEmployee(String employeeNumber, Integer year);

    /**
     * 휴가 신청 등록
     *
     * 휴가 신청 정보를 저장하고 생성 결과를 반환한다.
     *
     * @param request 휴가 신청 요청 DTO
     * @return 휴가 신청 생성 응답 DTO
     */
    VacationCreateResponseDTO createVacationRequest(VacationCreateRequestDTO request);
}