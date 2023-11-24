# 개발 환경 세팅 방법
## application-xx.properties 적용 방법
1. application.properties 에 해당 내용을 입력한다.
> spring.profiles.active=local or dev or server
> spring.profiles.include=local or dev or server

- application.properties 설정 파일이 없을 시
    1. src/main/resources 폴더 우클릭
    2. 새로 만들기(New) -> 리소스 번들(Resource Bundle)
    3. 리소스 번들 기본이름: application, 확인

2. application.properties 에 세팅 환경에 따라 설정 값을 추가한다.
    - a.Database config (DB 설정)
    > URL : JDBC URL 값 (ex. jdbc:mysql://localhost:5555/myDB)
    > driver-class-name : DB 드라이버 클래스 이름 (ex. com.mysql.jdbc.Driver)
    > username :
   
    - b.JPA hibernate dialect config (JPA 방언 설정)
    > spring.jpa.properties.hibernate.dialect : 연동할 DB의 종류, 버전에 맞게 dialect 설정 (ex. org.hibernate.dialect.MySQL8Dialect)
 
    - c.File upload directory config (파일 업로드 경로 설정)
    > upload.dir : '/src/main/resources/upload' 폴더의 절대 경로 + '/' (ex. C:/Users/.../PTU_TEAM/src/main/resources/upload/)

