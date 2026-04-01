package kr.co.cloudStudy.auth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "refresh_token")
public class RefreshToken {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String employeeNumber;
	
	@Column(nullable = false, length = 1000)
	private String token;
	
	@Column(nullable = false)
	private LocalDateTime expiryDate;
	
	public void updateToken(String token, LocalDateTime expiryDate) {
		this.token = token;
		this.expiryDate = expiryDate;
	}
}
