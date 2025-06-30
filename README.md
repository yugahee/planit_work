## planit_work
플랜잇스퀘어 백엔드 개발자 채용과제
- h2 콘솔 : http://localhost:9090/h2-console/login.jsp

## 개발환경
- framework : spring boot 3.4.8
- version : java 21
- ORM : JPA
- IDE : IntelliJ

## 빌드 & 실행방법
1. 프로젝트 클론 또는 다운받아주세요.
2. 애플리케이션 빌드 및 실행해주세요.
  - macOS / Linux
   ./gradlew bootRun
  - Windows
   gradlew.bat bootRun
3. swagger를 통해 테스트를 진행해주세요.
   - api 문서(swagger) : http://localhost:9090/swagger-ui/index.html
   - h2 콘솔 : http://localhost:9090/h2-console/login.jsp


## API 명세 요약
1. 나라, 공휴일 데이터 적재 API
   외부 API를 통해서 나라, 공휴일 데이터를 가져와서 일괄 적재를 합니다.
   - Endpoint : POST http://localhost:9090/api/v1/holidays/allSave
   - Response : 성공여부(success)를 반환합니다.
2. 공휴일 검색 API
   특정 국가, 연도, 타입을 검색합니다.
   - Endpoint : GET http://localhost:9090/api/v1/holidays/select
   - parameter :
       - currPage : 페이지
       - rowPerPage : 한 페이지에 노출되는 데이터 수
       - year : 연도
       - countryCode : 나라코드
       - types : 타입
   - Response : 성공여부(success)와 검색 데이터(data)를 반환합니다.
3. 공휴일 재동기화 API
   외부 API를 재호출하여 지정한 국가와 연도의 공휴일 데이터를 갱신합니다.
   - Endpoint : POST http://localhost:9090/api/v1/holidays/refresh
   - parameter :
       - year : 연도
       - countryCode : 나라코드
   - Response : 성공여부(success)를 반환합니다.
4. 공휴일 삭제 API
   지정한 국가와 연도의 공휴일 데이터를 삭제합니다.
   - Endpoint : POST http://localhost:9090/api/v1/holidays/delete
   - parameter :
       - year : 연도
       - countryCode : 나라코드
   - Response : 성공여부(success)를 반환합니다.
5. 매년 데이터 자동 동기화 기능
   스케쥴링을 통해 매년 1월 2일에 전년도와 금년도의 공휴일 데이터를 자동 갱신합니다.

## API 문서 확인 방법 
1. 애플리케이션을 실행합니다.
2. Swagger UI 주소로 접속해주세요.
   URL: http://localhost:9090/swagger-ui/index.html
3. 상세한 정보를 확인하고 Try it out으로 API를 직접 실행시켜주세요.
