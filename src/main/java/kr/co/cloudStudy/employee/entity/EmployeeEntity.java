package kr.co.cloudStudy.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema; // 추가됨
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employee")
@Schema(description = "직원 정보 엔티티") // 클래스 설명 추가
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    @Schema(description = "직원 고유 번호 (PK)", example = "1")
    private Long id; // 직원번호 (BIGINT, PK)

    @Column(name = "name", nullable = false, length = 50)
    @Schema(description = "직원 성함", example = "홍길동")
    private String name; // 이름 (VARCHAR(50))

    @Column(name = "position", nullable = false, length = 50)
    @Schema(description = "현재 직책", example = "사원")
    private String position; // 직책 (VARCHAR(50))

    @Column(name = "status", nullable = false)
    @Builder.Default
    @Schema(description = "재직 상태 (0:재직, 1:휴직, 2:퇴사)", example = "0")
    private Integer status = 0; // 상태 (TINYINT)

    @Column(name = "email", nullable = false, length = 100)
    @Schema(description = "회사 이메일", example = "hong@example.com")
    private String email; // 이메일 (VARCHAR(100))

    @Column(name = "password", nullable = false, length = 255)
    @Schema(description = "로그인 비밀번호 (암호화 저장됨)", example = "$2a$10$...")
    private String password; // 비밀번호 (VARCHAR(255))

    @Column(name = "profile_img", nullable = false, length = 255)
    @Schema(description = "프로필 이미지 URL/경로", example = "/images/profile/1.png")
    private String profileImg; // 프로필 이미지 (VARCHAR(255))

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, 
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "데이터 생성 일시", example = "2024-03-25T17:52:00")
    private LocalDateTime createdAt; // 생성일

    @LastModifiedDate
    @Column(name = "updated_at", 
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Schema(description = "마지막 수정 일시", example = "2024-03-25T17:52:00")
    private LocalDateTime updatedAt; // 수정일

    // 아직 없어서 주석처리 해놓았습니다.
//    @ManyToOne(fetch = FetchType.LAZY) 
//    @JoinColumn(name = "dept_id", nullable = false) 
//    @Schema(description = "소속 부서 정보 (객체 연관 관계)") 
//    private DepartmentEntity department; 
}