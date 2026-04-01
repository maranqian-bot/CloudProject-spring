package kr.co.cloudStudy.auth.controller.docs;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.dto.LogoutRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueResponseDTO;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;

public interface AuthControllerDocs {
	
	@Operation(
            summary = "로그인",
            description = "사번과 비밀번호를 입력받아 JWT 액세스 토큰과 리프레시 토큰을 발급합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청값",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "비밀번호 불일치 또는 인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            )
    })
    ResponseEntity<ApiResponseDTO<LoginResponseDTO>> login(LoginRequestDTO requestDTO);

    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 검증한 뒤 새로운 액세스 토큰을 재발급합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "토큰 재발급 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청값",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않거나 만료된 리프레시 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            )
    })
    ResponseEntity<ApiResponseDTO<ReissueResponseDTO>> reissue(ReissueRequestDTO requestDTO);

    @Operation(
            summary = "로그아웃",
            description = "리프레시 토큰을 삭제하여 로그아웃 처리합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청값",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> logout(LogoutRequestDTO requestDTO);

}
