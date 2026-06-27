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
| DB | H2 (In-Memory) |

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
│   ├── WorkoutDetail.java
│   └── WorkoutType.java
├── dto
│   ├── WorkoutRequest.java
│   ├── WorkoutResponse.java
│   ├── WorkoutDetailRequest.java
│   └── WorkoutDetailResponse.java
├── exception
│   └── WorkoutNotFoundException.java
├── repository
│   ├── WorkoutRepository.java
│   ├── JpaWorkoutRepository.java
│   └── MemoryWorkoutRepository.java
└── service
└── WorkoutService.java

---

## 📦 설계 의도

### 1) Controller

**WorkoutController**
- 사용자의 HTTP 요청을 받아 JSON으로 응답하는 REST 컨트롤러
- `GET /api/workouts` — 전체 운동 기록 목록 반환
- `POST /api/workouts` — 운동 기록 등록 후 저장된 데이터 반환
- `GET /api/workouts/{id}` — id로 단건 조회
- `PUT /api/workouts/{id}` — 운동 기록 수정
- `DELETE /api/workouts/{id}` — 운동 기록 삭제
- CORS 설정 적용 — 프론트엔드에서의 API 호출 허용

**GlobalExceptionHandler**
- `@RestControllerAdvice`로 전역 예외 처리 담당
- `WorkoutNotFoundException` 발생 시 404, `MethodArgumentNotValidException` 발생 시 400을 구조화된 JSON으로 반환
- 응답 필드 순서를 보장하기 위해 `LinkedHashMap` 사용 — `Map.of()`는 순서를 보장하지 않음

---

### 2) Domain

**Workout**
- 운동 기록을 표현하는 핵심 도메인 엔티티
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)` — JPA 프록시 생성을 위한 기본 생성자를 외부에서 직접 호출하지 못하도록 막음
- 정적 팩토리 메서드 `create()`로만 생성 — 생성자를 직접 노출하지 않아 객체 생성 방식을 통제
- `withId()`는 `MemoryWorkoutRepository`에서 id를 직접 주입해야 하는 경우에만 사용 — JPA 환경에서는 `create()`만 사용
- `update()`로 필드 변경 — setter를 열어두지 않고 의미 있는 메서드로 상태 변경을 캡슐화
- `addDetail()`로 `WorkoutDetail` 추가 — 연관관계 편의 메서드로, `details` 리스트 추가와 `WorkoutDetail.assignWorkout()` 호출을 한 곳에서 처리해 양방향 연관관계 일관성 유지

**WorkoutDetail**
- 운동 세부 기록을 표현하는 엔티티 (1:N 관계에서 N 쪽)
- `assignWorkout()`의 접근제어자를 `package-private`으로 제한 — `Workout.addDetail()`을 통해서만 호출되어야 하므로 외부에서 직접 호출을 막음
- `@ManyToOne(fetch = FetchType.LAZY)` — 즉시 로딩 대신 지연 로딩을 사용해 불필요한 쿼리 방지

---

### 3) DTO

**WorkoutRequest / WorkoutDetailRequest**
- `record` 타입으로 불변 객체 — 요청 데이터는 변경될 이유가 없으므로 불변으로 설계
- `@Valid`를 `WorkoutRequest.details` 필드에도 선언 — 중첩 DTO 검증을 위해 필요하며, 없으면 `WorkoutDetailRequest`의 검증 어노테이션이 동작하지 않음
- `@PastOrPresent` — 미래 날짜의 운동 기록 등록을 막음

**WorkoutResponse / WorkoutDetailResponse**
- 엔티티를 뷰에 직접 노출하지 않기 위해 분리 — 엔티티 구조 변경이 API 응답에 영향을 주지 않도록 격리
- 정적 팩토리 메서드 `from()`으로 엔티티에서 변환 — 변환 로직을 DTO 안에 캡슐화

---

### 4) Repository

**WorkoutRepository (인터페이스)**
- 어댑터 패턴 적용 — 서비스가 구현체(JPA, 메모리)에 의존하지 않고 인터페이스에만 의존하도록 설계
- 저장소 구현을 교체해도 서비스 코드를 수정할 필요 없음

**JpaWorkoutRepository**
- `EntityManager`를 직접 사용 — `Spring Data JPA`의 기본 메서드 대신 JPQL을 직접 작성해 N+1 문제를 명시적으로 해결
- `findById()`, `findAll()` 모두 `LEFT JOIN FETCH`로 `details`를 한 번의 쿼리로 함께 조회 — `details`가 없는 `Workout`도 누락되지 않도록 `LEFT JOIN` 사용
- `save()`는 `persist()`만 담당 — update는 서비스에서 `findById()`로 가져온 영속 상태 엔티티를 수정하면 트랜잭션 종료 시 더티체킹으로 자동 반영되므로 `merge()`가 필요 없음
- `delete()`도 `findById()`로 가져온 영속 상태 엔티티를 그대로 넘기므로 `em.contains()` 분기 없이 `em.remove()` 직접 호출

**MemoryWorkoutRepository**
- 테스트 및 로컬 개발 환경용 인메모리 저장소
- `ConcurrentHashMap`과 `AtomicLong` 사용 — 멀티스레드 환경에서의 동시성 문제 방지
- `@Qualifier("memoryWorkoutRepository")`로 JPA 구현체와 구분

---

### 5) Service

**WorkoutService**
- `@Transactional(readOnly = true)`를 클래스 레벨에 선언하고, 쓰기 작업 메서드에만 `@Transactional`을 재선언 — 조회 성능 최적화와 실수로 인한 데이터 변경 방지
- `updateWorkout()`에서 `details.clear()` 후 새 detail을 추가하는 방식(clear-and-replace) 사용 — 어떤 항목이 추가/삭제됐는지 추적하지 않고 전체 교체, `orphanRemoval = true`로 제거된 detail은 자동 삭제

---

### 6) Exception

**WorkoutNotFoundException**
- `RuntimeException` 상속 — 체크 예외 대신 언체크 예외로, 서비스/컨트롤러에서 명시적으로 catch하지 않아도 `GlobalExceptionHandler`에서 처리
- 에러 메시지에 id를 포함해 어떤 리소스가 없는지 명확하게 전달

---

## ✅ 구현 기능
- 운동 기록 등록 / 전체 조회 / 단건 조회 / 수정 / 삭제 API
- 운동 세부 기록 (WorkoutDetail) 1:N 관계 관리
- 서버 사이드 유효성 검증 (`@Valid`, 중첩 DTO 검증)
- N+1 문제 해결 (JPQL fetch join)
- 전역 예외 처리 (`@RestControllerAdvice`)
- JPA / 메모리 저장소 교체 가능한 어댑터 패턴
- CORS 설정으로 프론트엔드 연동