package kr.co.cloudStudy.auth.service;

import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.dto.ReissueRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueResponseDTO;

public interface AuthService {
	
	LoginResponseDTO login(LoginRequestDTO requestDTO);
	
	ReissueResponseDTO reissue(ReissueRequestDTO requestDTO);
	
	void logout(String refreshToken);  
}
