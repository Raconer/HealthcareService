# HealthcareService

## 🚀 실행 방법
```shell
# 실행 권한 부여
# chmod +x startup.sh

# 실행
./startup.sh
````

## API 문서 (Swagger)
- 주소: http://localhost:8080/api/swagger/index.html

## data.sql 정보
- 서버 실행시 데이터 input
- PATH : ./src/main/resources/data.sql
  - 테스트 계정 비밀번호: 1q2w3e4r!@#$

### ERD
![ERD](./img/health.png)

## 테스트 방법
- test.http 파일 실행하여 API 요청 테스트 가능
- root에 위치

## 발생한 이슈 및 해결 방법
### 걸음 수 필드 타입 문제
#### 문제
> 응답/요청 값에 소수점 타입이 포함
- "steps": "688.5509846105425" 

#### 해결

![파싱에러](./img/parsingerror.png)

> STEP이 간헐적으로 데이터가 전송 되고 있으니 오류 데이터만 "parse_error_log" DB Table에 저장후 수정 필요

### 기간(period) 필드 포맷 불일치
#### 문제
> 기간이 포맷에 맞지 않게 Reqeust 되는 문제
```json
// 예1
{
  "period": {
    "from": "2024-12-16 22:30:00",
    "to": "2024-12-16 22:40:00"
  }
}
```
```json
// 예 2
{
    "period": {
      "from": "2024-11-14T21:20:00+0000",
      "to": "2024-11-14T21:30:00+0000"
    }
}
```

#### 해결
> com.health.care.health.controller.dto.request.deserializer.DateTimeDeserializer
> 에서 DateFormat 체크


```kotlin
private val formats = listOf(
   DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
   DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
)

override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
   val text = p.text.trim()
   for (fmt in formats) {
      try {
         return LocalDateTime.parse(text, fmt)  // 위 리스트 포맷안에 포함되어 있다면 LocalDateTime으로 변환후 Return
      } catch (_: Exception) {}
   }
   throw IllegalArgumentException("Unsupported date format: $text")
}
```

### 제출 산출물

1. 소스코드(코멘트 추가)
    - GitHub 레포지토리 주소 제출 혹은 소스파일 압축해서 제출
2. 데이터베이스 설계 ERD (코멘트 추가)
3. 데이터 조회 결과 제출 (Daily/Monthly 레코드키 기준)
4. 구현 방법 및 설명
    - 프로젝트 구조 설명
   
    - 필드 설명
        - steps - 걸음수(int)
        - calories - 소모 칼로리(float)
        - distance - 이동거리(float)
        - recordkey - 사용자 구분 키(varchar)