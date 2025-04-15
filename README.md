## Itcen Core 공통 프레임워크 개발및 MSA도입 프로젝트.
### 인증 인가서버 -> 로그인 성공한 요청에 대해해 JWT토큰 발급 전용 서버.

## Tech
Java21
SpringBoot
SpringSecurity
Docker


## 기능 
1. 아이디/패스워드 로그인 기능 구현

2. MemberServer <-> AuthServer 분리 진행(2025.03.27)
   ->  공통단 CommonClass도입 -> UserEntity 공통단에서 가져와서 사용.

3. 구글 소셜로그인 기능 도입.(2025.04.14)
