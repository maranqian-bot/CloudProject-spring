package kr.co.cloudStudy.auth.service;

import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;

public interface AuthService {
	
	LoginResponseDTO login(LoginRequestDTO requestDTO);
}
