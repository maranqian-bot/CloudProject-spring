package kr.co.cloudStudy.auth.controller.docs;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.co.cloudStudy.auth.dto.EmailCodeSendRequestDTO;
import kr.co.cloudStudy.auth.dto.EmailCodeVerifyRequestDTO;
import kr.co.cloudStudy.auth.dto.PasswordResetConfirmRequestDTO;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;

public interface EmailCodeControllerDocs {

	@Operation(
            summary = "이메일 인증 코드 발송",
            description = "입력한 이메일로 인증 코드를 발송합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "인증 코드 발송 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청값 (이메일 형식 오류 등)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "이메일 발송 실패 또는 서버 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> sendEmailCode(EmailCodeSendRequestDTO requestDTO);


    @Operation(
            summary = "이메일 인증 코드 검증",
            description = "사용자가 입력한 인증 코드가 유효한지 검증합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "이메일 인증 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청값 또는 인증 코드 불일치",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "인증 코드가 존재하지 않음 (만료 또는 미발송)",
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
    ResponseEntity<ApiResponseDTO<Void>> verifyEmailCode(EmailCodeVerifyRequestDTO requestDTO);


    @Operation(
            summary = "비밀번호 재설정",
            description = "이메일 인증이 완료된 사용자에 대해 비밀번호를 재설정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "비밀번호 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청값 또는 인증되지 않은 이메일",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 이메일의 사용자 없음",
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
    ResponseEntity<ApiResponseDTO<Void>> resetPassword(PasswordResetConfirmRequestDTO requestDTO);
}
