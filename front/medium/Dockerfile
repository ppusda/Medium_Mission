# 1단계: 빌드 환경 설정
# Node.js 공식 이미지를 사용하여 빌드 스테이지를 생성
FROM node:16-alpine AS build

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사
COPY package.json package-lock.json ./

# 의존성 설치
RUN npm install

# 애플리케이션 소스 복사
COPY . .

# SvelteKit 애플리케이션 빌드
RUN npm run build

# 2단계: 실행 환경 설정
# 빌드 스테이지에서 생성된 파일을 nginx 서버에 배포
FROM nginx:alpine

# 빌드 스테이지에서 빌드된 파일을 nginx의 서브 디렉토리로 복사
COPY --from=build /app/build /usr/share/nginx/html

# Nginx 설정 파일 복사
COPY nginx.conf /etc/nginx/nginx.conf

# 80 포트 개방
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
