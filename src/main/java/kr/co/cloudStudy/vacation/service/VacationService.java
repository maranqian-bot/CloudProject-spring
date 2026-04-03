package kr.co.cloudStudy.vacation.service;

import java.util.List;

import kr.co.cloudStudy.vacation.dto.MyVacationHistoryDTO;
import kr.co.cloudStudy.vacation.dto.PendingVacationApprovalDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationDecisionResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRejectRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestEmployeeResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListSummaryDTO;

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
     * @param approverEmployeeNumber 승인자 사번
     * @return 승인 대기 휴가 신청 DTO 리스트
     */
    List<PendingVacationApprovalDTO> getPendingApprovals(String approverEmployeeNumber);

    /**
     * 휴가 관리 페이지 전체 데이터 조회
     *
     * 상단 요약 정보, 내 휴가 신청 이력, 승인 대기 목록을 하나로 묶어 반환한다.
     *
     * @param employeeNumber 조회할 직원 사번
     * @param approverEmployeeNumber 승인자 사번
     * @param year 조회할 연도
     * @return 휴가 관리 페이지 응답 DTO
     */
    VacationManagementResponseDTO getVacationManagementPage(
            String employeeNumber,
            String approverEmployeeNumber,
            Integer year
    );

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

    /**
     * 휴가 신청 목록 페이지 조회
     *
     * 승인자가 확인해야 하는 휴가 신청 목록을 페이지 단위로 반환한다.
     *
     * @param approverEmployeeNumber 승인자 사번
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param type 휴가 유형 필터
     * @return 휴가 신청 목록 응답 DTO
     */
    VacationRequestListResponseDTO getVacationRequestList(
            String approverEmployeeNumber,
            Integer page,
            Integer size,
            String type
    );

    /**
     * 휴가 신청 목록 페이지 상단 요약 정보 조회
     *
     * 승인 대상 신청 건수 및 관련 요약 정보를 반환한다.
     *
     * @param approverEmployeeNumber 승인자 사번
     * @return 휴가 신청 목록 요약 DTO
     */
    VacationRequestListSummaryDTO getVacationRequestListSummary(String approverEmployeeNumber);

    /**
     * 휴가 신청 승인 처리
     *
     * 승인자가 특정 휴가 신청을 승인한다.
     *
     * @param vacationId 휴가 신청 ID
     * @param approverEmployeeNumber 승인자 사번
     * @return 승인 처리 결과 DTO
     */
    VacationDecisionResponseDTO approveVacationRequest(Long vacationId, String approverEmployeeNumber);

    /**
     * 휴가 신청 반려 처리
     *
     * 승인자가 특정 휴가 신청을 반려한다.
     *
     * @param vacationId 휴가 신청 ID
     * @param request 반려 요청 DTO
     * @param approverEmployeeNumber 승인자 사번
     * @return 반려 처리 결과 DTO
     */
    VacationDecisionResponseDTO rejectVacationRequest(
            Long vacationId,
            VacationRejectRequestDTO request,
            String approverEmployeeNumber
    );
}