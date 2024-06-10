## Redis 조금만 맛보기 👀
- 목표 - 서로 다른 두 개의 서버를 Redis를 사용하여 공유해 보기
<hr>

#### Redis 설정
1. Redis 홈페이지 회원가입 https://redis.io/
2. Databases 생성하기
![이미지](/img/num1.png)<br>
- 생성한 Database의 General에서 application.yaml에서 사용할 host와 port번호를 알 수 있다
![이미지](/img/num2.png)
- 생성한 Database의 Security에서 application.yaml에서 사용할 username과 password를 알 수 있다
![이미지](/img/num3.png)
3. application.yaml 설정하기
    - Redis Cloud에서 만들어진 `Redis 서버 접속 정보`를 사용
    - Redis의 서버 접속 정보를 바탕으로 `.application.yaml`작성하기
![이미지](/img/num4.png)
4.  Intellij에 Redis 연결
![이미지](/img/num5.png)
5. build.gradle에 의존성 추가
   - implementation 'org.springframework.session:spring-session-data-redis'
6. RedisConfig 클래스에 `@EnableRedisHttpSession`추가
<hr>

#### 8081 포트 열기
서로 다른 두 개의 서버를 Redis를 사용하여 공유해 보기 위해 8081번 포트를 열어보자
1. Configration의 Edit.. 클릭
2. RedisApplication의 이름을 8080으로 바꿔주기
3. 8080을 복사해서 8081포트 만들어주기
4. Modify options/Add VM options 클릭
5. `-Dserver.prot=8081`입력해 주기
![이미지](/img/num6.png)
<hr>

#### Redis 맛보기
1. #### Redis로 간단한 데이터 저장
   - Redis는 Key - Value의 형태로 데이터를 저장한다 <br>
   ![이미지](/img/num7.png)
   
2. #### 서로 다른 두 개의 서버를 사용하여 비교해 보기
   - ####  Redis 사용 전
     - 8080포트에 Key - Value값 넣어주기
     ![이미지](/img/num8.png)
     - 8080포트에서 key값 요청 시 / Status:`200 OK`
     ![이미지](/img/num9.png)
     - 8081포트에서 key값 요청 시 / Status: `404 Not Found`
     ![이미지](/img/num10.png)
   - #### 이러한 문제가 일어나는 이유
     - Spring Boot에서 세션을 만들 경우에는 내장되어 있는 Tomcat에서 `JSESSIONID`를 사용하는데,
       8080과 8081의 서로 다른 톰캣이 실행되었고, 이 둘은 서로 세션을 공유하지 않는다
   - #### Redis를 사용하여 문제 해결하기
     - 8080포트에 Key - Value값 넣어주기
     ![이미지](/img/num11.png)
     - 8081포트에서 key값 요청 시 / Status:`200 OK`
     ![이미지](/img/num12.png)
   - #### 정리
     - 8080포트에서 입력한 값이 8080, 8081포트 모두에서 똑같은 응답을 받을 수 있는 이유는, 
     Redis라는 중간 매개체가 서로의 세션에 대한 정보를 공유해 줌으로써 같은 응답을 받을 수 있다.


   
    

