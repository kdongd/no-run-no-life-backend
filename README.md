# 🥊 NO RUN NO LIFE
> 기록이 실력을 만든다. 러닝과 복싱 기록을 남기는 웹 서비스.

---

## 📌 프로젝트 소개
- 오늘의 운동을 기록하고, 어제보다 나은 나를 확인하세요.
- 러닝과 복싱, 두 종목의 기록을 한 곳에서 관리하고 나만의 운동 루틴을 쌓아가는 서비스입니다.
- Spring Boot REST API 서버로, 프론트엔드와 분리된 구조로 운영됩니다.
- Spring MVC, JPA, Spring Security를 6주에 걸쳐 단계별로 적용하며 처음부터 직접 만들어가는 프로젝트입니다.

---

## 🛠 기술 스택
| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot |
| API | REST API |
| Build | Gradle |
| DB | 메모리 저장소 (추후 H2 → MySQL 예정) |

---

## ⚙️ 실행 방법
```bash
./gradlew bootRun
```
서버 실행 후 `http://localhost:8080` 에서 API 사용 가능

프론트엔드 레포: [no-run-no-life-frontend](https://github.com/kdongd/no-run-no-life-frontend)

---

## 📁 프로젝트 구조

src/main/java/com/kdongd/norunnolife
├── controller
│   ├── WorkoutController.java
│   └── GlobalExceptionHandler.java
├── domain
│   ├── Workout.java
│   └── WorkoutType.java
├── dto
│   ├── WorkoutRequest.java
│   └── WorkoutResponse.java
├── repository
│   └── MemoryWorkoutRepository.java
└── service
└── WorkoutService.java

---

## 📦 클래스 설명

### 1) Controller
**WorkoutController**
- 사용자의 HTTP 요청을 받아 JSON으로 응답하는 REST 컨트롤러
- `GET /api/workouts` — 전체 운동 기록 목록을 JSON으로 반환
- `POST /api/workouts` — 운동 기록 데이터를 JSON으로 받아 저장 후 저장된 데이터 반환
- `GET /api/workouts/{id}` — 특정 운동 기록을 id로 조회
- CORS 설정 적용 — 프론트엔드에서의 API 호출 허용

**GlobalExceptionHandler**
- `@RestControllerAdvice`로 전역 예외 처리 담당
- `NoSuchElementException` 발생 시 404 상태코드와 메시지 반환

### 2) Domain
**Workout**
- 운동 기록을 표현하는 도메인 클래스
- `id`, `type`(운동 종류), `durationMinutes`(운동 시간), `memo`(메모), `workoutDateTime`(운동 일시) 필드 보유
- 정적 팩토리 메서드 패턴 사용 (`create()`, `withId()`)

**WorkoutType**
- 운동 종류를 나타내는 enum
- `RUNNING`(러닝), `BOXING`(복싱) 두 가지 값 보유
- String 대신 enum을 쓰는 이유: 허용되지 않는 값 입력 방지, 타입 안전성 보장

### 3) DTO
**WorkoutRequest**
- 운동 기록 등록 시 클라이언트로부터 받는 요청 데이터
- Bean Validation 적용 — `@NotNull`, `@Min`
- `record` 타입으로 불변 객체

**WorkoutResponse**
- 클라이언트에게 반환하는 운동 기록 응답 데이터
- `Workout` 도메인 객체로부터 변환하는 정적 팩토리 메서드 `from()` 보유
- `record` 타입으로 불변 객체

### 4) Repository
**MemoryWorkoutRepository**
- DB 없이 `Map<Long, Workout>` 기반으로 데이터를 메모리에 저장
- `save()` — id를 자동 증가시켜 저장
- `findAll()` — 전체 기록 반환
- `findById()` — id로 특정 기록 조회
- 한계: 서버 재시작 시 모든 데이터 초기화 (2주차에 JPA로 교체 예정)

### 5) Service
**WorkoutService**
- Controller와 Repository 사이에서 비즈니스 로직을 담당
- `createWorkout()` — 운동 기록 저장
- `getWorkouts()` — 전체 운동 기록 조회
- `getWorkout()` — id로 특정 운동 기록 조회, 없으면 `NoSuchElementException` 발생

---

## ✅ 구현 기능
- 운동 기록 등록 API (러닝 / 복싱)
- 운동 기록 전체 조회 API
- 운동 기록 단건 조회 API
- 서버 사이드 유효성 검증 (`@Valid`)
- 전역 예외 처리 (`@RestControllerAdvice`)
- CORS 설정으로 프론트엔드 연동