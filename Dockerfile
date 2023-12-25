FROM ghcr.io/graalvm/graalvm-community:21

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드와 Gradle 래퍼 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Gradle 래퍼에 실행 권한 부여
RUN chmod +x ./gradlew

# 애플리케이션 빌드
RUN ./gradlew clean build -x test

RUN mv build/libs/*.jar /app.jar

# 현재 폴더에서 app.jar 빼고 전부 제거
RUN rm -rf *
RUN mv /app.jar /app/app.jar

# 실행할 JAR 파일 지정
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]