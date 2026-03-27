# HR Management System (근태 & 인사 관리 시스템)


![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Hibernate-59666C?logo=hibernate&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?logo=springsecurity&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-Annotation-red)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?logo=swagger&logoColor=black)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)
![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20S3%20%7C%20CloudFront-232F3E?logo=amazonaws)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-blue?logo=githubactions)

---

## 프로젝트 소개

실무 환경을 고려하여 설계한 **근태 및 인사 관리 시스템**

- 프론트/백엔드 분리 구조
- AWS 기반 인프라
- CI/CD (OIDC 기반 배포 파이프라인)

---

## 시스템 아키텍처
![제목 없는 다이어그램](https://github.com/user-attachments/assets/7b892743-d296-4c54-8212-dfbb147f3199)

---

## ERD

<img width="1617" height="754" alt="image" src="https://github.com/user-attachments/assets/d3d6e44e-67a7-464c-86bf-be4d843b6e6a" />


---

## 주요 기능

- 로그인 / 인증
- 직원 관리
- 부서 관리
- 근태 관리
- 휴가 관리

---

## 트러블 슛팅 기록


---

## 기술 스택

### Backend
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- Lombok
- Swagger

### Infra
- AWS EC2
- AWS S3
- AWS CloudFront
- MySQL
  
---

## 로컬 실행 방법

본 프로젝트는 데이터베이스 정보를 코드에 직접 작성하지 않고 환경 변수(Environment Variables)로 설정하여 사용합니다.

프로젝트 실행 전에 아래 환경 변수를 반드시 설정해주세요.

#### 필수 환경 변수

- DB_URL
- DB_USERNAME
- DB_PASSWORD

#### 예시

DB_URL=jdbc:mysql://localhost:3306/cloud_project  
DB_USERNAME=root  
DB_PASSWORD=your_password  

#### 주의사항

환경 변수를 설정하지 않으면 프로젝트 실행 시 오류가 발생합니다.

---

## 팀원 및 역할
| 이름 | 역할 | 기능 구현 |
|------|------|------|
| 마은재 ([maranqian-bot](https://github.com/maranqian-bot)) | 팀장 | CICD 파이프라인 구축, 휴가관리 및 대시보드 기능 구현 |
| 김성원 ([gungang1212-tech](https://github.com/gungang1212-tech)) | 팀원 | 채울 예정 |
| 이가연 ([ixxveon](https://github.com/ixxveon)) | 팀원 | AWS S3 + CloudFront 구축, 근태 관리 및 로그인 기능 구현 |
| 신보라 ([sbr7518-bit](https://github.com/sbr7518-bit)) | 팀원 | WAS EC2(MySQL) 구축, 부서 관리 기능 구현 |
