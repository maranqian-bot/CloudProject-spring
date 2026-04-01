package kr.co.cloudStudy.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.auth.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findByEmployeeNumber(String employeeNumber);
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByEmployeeNumber(String employeeNumber);
}
