## MEDIUM_MISSION PROJECT

### Medium 사이트를 바탕으로 자유롭게 구현하는 미션 프로젝트
- ~~[Medium 사이트 이용해보기](https://medium.bbgk.me/)~~
- `현재는 운영하지 않고 있습니다. 아래 구현 내용을 참고 해주시길 바랍니다.`

## ⚙ 기술 스택
![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/fe6eb702-5946-4b5f-8379-ab69595274da)

**Frontend**

- Language : Javascript
- Framework : SvelteKit
- Library : Tailwind, Daisy UI, Toast UI Editor
- Deploy: NCP, Docker, Nginx Proxy Manager
- CI/CD : Github Actions

**Backend**

- Language : Java
- Library & Framework : Spring Boot, Spring Security, QueryDSL
- Database : H2, MySQL, Redis
- ORM : JPA
- Deploy: NCP, Docker, Nginx Proxy Manager
- CI/CD : Github Actions


## 🎞 배포 
![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/4824f580-a63a-4c1b-b51f-55bfaf638e14)


## 📚 구현 내용

- 회원 
  - 회원가입
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/7c5b593c-4e8e-4ef7-95f3-00d133bd9535)
  - 로그인
    - JWT 토큰 방식 이용
    - AccessToken, RefreToken(Redis) 구현 (RTR 기법 적용)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/8a0454ff-7fc5-4ba2-83eb-89701ca62825)
  - 회원정보 수정
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/9d3624d5-34d4-406c-8bca-ffeb0773e77b)
  - 회원 별 글 모아보기
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/1e2b1e31-6760-4560-a47c-2e895935144f)
  - 멤버쉽 가입/해지
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/79e754f8-25ed-4b02-9482-a5659e6f7e4e)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/d812a54d-4d8a-4fcd-ac06-1f9e0641f525)

- 게시글
  - 글 작성
    - MarkDown Editor(Toast UI) 이용
    - Add Image Blob Hook 이용 (이미지 링크 형식으로 제공)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/65ca7284-14f8-49d6-a26f-d167b340cf3b)
  - 글 전체/단일 조회
    - prismjs (Code Highlighter)
    - remark, strip-markdown (MarkDown 문법 평문화)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/355bf20e-d04d-4c88-b631-27afb5db7d2b)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/d0e68337-8842-4285-9486-ea6eb1dae111)
  - 글 수정
    - MarkDown Editor(Toast UI) 이용
    - Add Image Blob Hook 이용 (이미지 링크 형식으로 제공)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/aa99597c-afd3-4cea-a545-08c451bed77f)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/b1d78b12-22d9-4dc7-bc4b-c268a3533600)
  - 글 조회수, 글 추천
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/bf8b1f1b-4eaa-4eab-b6c7-72a342ce470d)
  - 글 검색 (QueryDSL)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/b9ff7339-4e3a-405d-9fcf-58c627f995c2)
  - 최근 일주일 간 추천 수 많은 글 조회 (QueryDSL)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/d3cbf868-3e68-4757-84e6-129cc96ee174)
  - 유료 글 작성/전환 (멤버쉽 회원만 작성, 열람 가능)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/2a8b222a-c3d8-47d4-a6dd-82a7855d522a)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/1d848698-0366-4d98-a340-434c71a50bee)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/67f3f9e8-76d7-456f-9b4c-4c9fd3e8c435)
  - 글 삭제
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/f0b55157-0b2b-4e84-9fef-0fc5449c0e54)
    
- 전체
  - toastr (검증, 예외처리, 안내 내용 toast 메시지로 출력)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/43f59728-0df7-46a1-a98d-d928e1a50433)
    ![image](https://github.com/ppusda/Medium_Mission_JoDongGuk/assets/51808344/0f8507a5-b520-472f-a122-7e2f9abd5cc0)


