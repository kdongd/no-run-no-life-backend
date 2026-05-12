# 🥊 NO RUN NO LIFE

> 기록이 실력을 만든다. 러닝과 복싱 기록을 남기는 웹 서비스.

---

## 📌 프로젝트 소개

 - 오늘의 운동을 기록하고, 어제보다 나은 나를 확인하세요.

 - 러닝과 복싱, 두 종목의 기록을 한 곳에서 관리하고 나만의 운동 루틴을 쌓아가는 서비스입니다.

 - Spring MVC, JPA, Spring Security를 6주에 걸쳐 단계별로 적용하며 처음부터 직접 만들어가는 프로젝트입니다.


---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot |
| View | Thymeleaf |
| CSS | Bootstrap 5 |
| Build | Gradle |
| DB | 메모리 저장소 (추후 H2 → MySQL 예정) |

---

## ⚙️ 실행 방법

```bash
./gradlew bootRun
```

서버 실행 후 브라우저에서 http://localhost:8080/workouts 접속

---

## 📁 프로젝트 구조

```
src/main/java/com/kdongd/norunnolife
├── controller
│   └── WorkoutController.java
├── domain
│   ├── Workout.java
│   └── WorkoutType.java
├── repository
│   └── MemoryWorkoutRepository.java
└── service
    └── WorkoutService.java
```

---

## 📦 클래스 설명

### 1) Controller

**WorkoutController**
- 사용자의 HTTP 요청을 받아 처리하고 뷰를 반환하는 역할
- `GET /workouts` — 전체 운동 기록 목록을 조회해 workouts.html 반환
- `GET /workouts/new` — 운동 기록 등록 폼(workout-form.html) 반환
- `POST /workouts` — 폼 데이터를 받아 저장 후 목록으로 redirect

### 2) Domain

**Workout**
- 운동 기록을 표현하는 도메인 클래스
- id, type(운동 종류), duration(운동 시간), memo(메모) 필드 보유
- Bean Validation 적용 — @NotNull, @Min, @NotBlank

**WorkoutType**
- 운동 종류를 나타내는 enum
- RUNNING(러닝), BOXING(복싱) 두 가지 값 보유
- String 대신 enum을 쓰는 이유: 허용되지 않는 값 입력 방지, 타입 안전성 보장

### 3) Repository

**MemoryWorkoutRepository**
- DB 없이 Map<Long, Workout> 기반으로 데이터를 메모리에 저장
- save() — id를 자동 증가시켜 저장
- findAll() — 전체 기록 반환
- 한계: 서버 재시작 시 모든 데이터 초기화 (2주차에 JPA로 교체 예정)

### 4) Service

**WorkoutService**
- Controller와 Repository 사이에서 비즈니스 로직을 담당
- createWorkout() — 운동 기록 저장
- getWorkouts() — 전체 운동 기록 조회

---

## ✅ 구현 기능

- 운동 기록 등록 (러닝 / 복싱)
- 운동 기록 목록 조회
- 서버 사이드 유효성 검증 (@Valid)


