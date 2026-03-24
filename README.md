## 📌 로컬 실행 방법

본 프로젝트는 데이터베이스 정보를 코드에 직접 작성하지 않고,
환경 변수(Environment Variables)로 설정하여 사용합니다.

프로젝트 실행 전에 아래 환경 변수를 반드시 설정해주세요.

### 🔧 필수 환경 변수

- DB_URL
- DB_USERNAME
- DB_PASSWORD

### 📍 예시

DB_URL=jdbc:mysql://localhost:3306/cloud_project  
DB_USERNAME=root  
DB_PASSWORD=your_password  

### ⚠️ 주의사항

환경 변수를 설정하지 않으면 프로젝트 실행 시 오류가 발생합니다.