package kr.co.cloudStudy.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStatsDto {
	@Schema(name = "총 정규직", example ="1,092")					
	private  String regularCount ;
	@Schema(name = "계약직", example ="156")
	private  String contractCount ;
	@Schema(name = "평균 근속 연수", example= "4.2년")
	private  String avgWorkingYears ;
	@Schema(name = "성장률(3분기)", example = "+12.4%")
	private String growthRate;
}
