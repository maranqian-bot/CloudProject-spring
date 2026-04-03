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

## 로그인 개발 환경 설정

비밀번호 재설정 및 이메일 인증 기능을 실행하려면 아래 두 가지 설정이 필요합니다.

- Redis 서버 실행
- Gmail 앱 비밀번호 생성 및 환경변수 등록

---

## 1. Redis 서버 실행

이 프로젝트는 이메일 인증 코드 저장, 인증 완료 상태 관리, Refresh Token 저장을 위해 Redis를 사용합니다.

### 1-1. Docker Desktop 설치

Redis를 가장 쉽게 실행하려면 Docker Desktop을 설치합니다.

- Docker Desktop 공식 문서: https://docs.docker.com/desktop/

설치 후 Docker Desktop을 실행해 주세요.

### 1-2. Redis 컨테이너 실행

터미널에서 아래 명령어를 실행합니다.

```bash
docker run -d --name cloudstudy-redis -p 6379:6379 redis
```

설명:
- -d: 백그라운드 실행
- --name cloudstudy-redis : 컨테이너 이름 지정
- -p 6379:6379 : 로컬 6379 포트와 Redis 컨테이너 포트 연결
- redis : Docker Hub의 공식 Redis 이미지 사용

### 1-3. 실행 확인

```bash
docker ps
```

정상 실행 중이면 cloudStudy-redis 컨테이너가 보입니다.

필요 시 Redis 컨테이너 시작/중지 명령어:
```bash
docker start cloudstudy-redis
docker stop cloudstudy-redis
```

삭제 후 다시 만들고 싶다면:
```bash
docker rm -f cloudstudy-redis
docker run -d --name cloudstudy-redis -p 6379:6379 redis
```

## Redis 환경변수 설정

예시:
```
spring.data.redis.host=localhost
spring.data.redis.port=6179
```

## Gmail 앱 비밀번호 생성

이 프로젝트는 이메일 인증 코드 발송을 위해 Gmail SMTP를 사용합니다.

Google 공식 안내에 따르면 앱 비밀번호는 일반 비밀번호 대신 사용하는 16자리 비밀번호이며, 사용하려면 Google 계정에 2단계 인증(2-Step Verification) 이 켜져 있어야 합니다. 또한 일부 계정(회사/학교 계정, 고급 보호 프로그램 등)에서는 앱 비밀번호를 사용할 수 없을 수 있습니다.

### 2-1. 2단계 인증 활성화

먼저 본인 Google 계정에서 2단계 인증을 활성화합니다.

### 2-2. 앱 비밀번호 생성

1. Google 계정 보안 설정으로 이동
2. 앱 비밀번호(App passwords) 메뉴 선택
3. Google 계정으로 다시 로그인
4. 앱 이름을 입력하고 생성
5. 생성된 16자리 비밀번호를 복사

*일반 Gmail 비밀번호를 코드나 설정 파일에 직접 넣으면 안 됩니다. 반드시 앱 비밀번호를 사용해야 합니다.

### 2-3. 환경변수 등록

예시:
```
MAIL_USERNAME=example@gmail.com
MAIL_PASSWORD=생성한_16자리_앱비밀번호
```
또는 application.properties에서 환경변수를 참조하도록 설정합니다.
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 3. 주의사항
- Docker Desktop이 실행 중이지 않으면 Redis 컨테이너가 동작하지 않습니다.
- Redis가 꺼져 있으면 이메일 인증 코드 저장/검증, Refresh Token 관련 기능에서 오류가 발생할 수 있습니다.
- Gmail 앱 비밀번호를 사용하지 않고 일반 계정 비밀번호를 사용할 경우 메일 전송이 실패할 수 있습니다.
- .env, application.properties, 환경변수 값 등 민감 정보는 Git에 올리지 마세요.

## 4. 빠른 실행 체크리스트
### Redis
```bash
docker start cloudstudy-redis
docker ps
```

### 메일
- Google 2단계 인증 활성화
- 앱 비밀번호 생성
- MAIL_USERNAME, MAIL_PASSWORD 등록

### 서버 실행 전 확인
- Redis 실행 여부 확인
- 메일 계정 환경변수 등록 여부 확인


## 팀원 및 역할
| 이름 | 역할 | 기능 구현 |
|------|------|------|
| 마은재 ([maranqian-bot](https://github.com/maranqian-bot)) | 팀장 | CICD 파이프라인 구축, 휴가관리 및 대시보드 기능 구현 |
| 김성원 ([gungang1212-tech](https://github.com/gungang1212-tech)) | 팀원 | AWS EC2(WAS) 구축, 직원관리 기능 구현 |
| 이가연 ([ixxveon](https://github.com/ixxveon)) | 팀원 | AWS S3 + CloudFront 구축, 근태관리 및 로그인 기능 구현 |
| 신보라 ([sbr7518-bit](https://github.com/sbr7518-bit)) | 팀원 | WAS EC2(MySQL) 구축, 부서관리 기능 구현 |
