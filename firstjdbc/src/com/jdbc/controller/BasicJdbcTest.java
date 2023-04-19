package com.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.model.vo.Member;

public class BasicJdbcTest {

	public static void main(String[] args) {
		//jdbc를 이용해서 오라클과 연동해보기
		//1. 오라클에서 제공하는 ojdbc.jar파일을 버전에 맞춰서 다운로드
		//2. 이클립스에서 프로젝트를 생성하고 생성된 프로젝트 라이브러리에 다운받은 jar파일을 추가
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//프로젝트(애플리케이션)에서 DB에 접속하기
		//1. jar파일이 제공하는 클래스가 있는지 확인하기 -> jar파일등록여부확인
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. DriverManager클래스를 이용해서 접속하는 객체를 만들어준다.
			// DriverManager클래스가 제공하는 getConnection() static 메소드를 이용해서
			// Connection객체를 가져온다. -> getConnection 메소드는 Connection객체를 반환한다.
			
			//getConnection 이용하기 -> 3개의 매개변수가 선언되어있음
			// 첫 번째 인수 : 접속할 DB의 주소, 버전정보, 포트번호 -> String
			//	 접속할 DBMS별로 문자열 패턴을 정해놓음!
			//	 오라클 : jdbc:oracle:thin:@ip주소:포트번호:버전
			// 두 번째 인수 : DB접속 계정명 -> String
			// 세 번째 인수 : DB접속 계정 비밀번호 -> String
			
			conn = DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:xe", "student", "student");
			
			//트렌젝션을 개발자가 직접 처리하겠다.(안하면 세션끝나면 자동커밋됨)
			conn.setAutoCommit(false);
			
			System.out.println("DB접속확인 완료!");
			
			//3. 접속된 DB에 sql문을 실행하고 결과를 가져와야한다.
			// sql문을 실행하기 위해서 실행할 객체가 필요함.
			// Statement, PreparedStatment : 문자열로 작성하는 sql구문을 연결된 DB에서 실행하는 객체
			// sql문을 실행하려면 Statement의 멤버메소드 executeQueary(), executeUpdate() 메소드를 이용한다.
			// SELECT : executeQueary("sql문") 실행 -> ResultSet 객체를 반환
			// INSERT, UPDATE, DELETE : executeUpdate("sql문") 실행 -> int 반환
			
			//실행하기
			//1. 쿼리문 작성하기
			// member 테이블에 있는 아이디가 admin인 사원 조회하기
			// SELECT * FROM MEMBER WHERE MEMBER_ID = 'admin';
			//문자열 변수에 sql문을 저장할 때는 ;을 생략한다!
//			String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = 'admin'";
			
			//등록된 회원전체가져오기
			String sql = "SELECT * FROM MEMBER";
			
			//2. Statement객체 가져오기
			// Connection클래스가 제공하는 멤버메소드인 createStatement() 메소드를 이용
			stmt = conn.createStatement();
			
			//3. 쿼리문 실행시키기
			// Statement가 제공하는 executeQuery() 실행하고 반환은 ResultSet 객체로 받는다.
			rs = stmt.executeQuery(sql);
			System.out.println(rs);
			
			//4. ResultSet
			// 반환된 SELECT문의 실행결과는 ResultSet객체가 제공하는 메소드를 이용해서 컬럼별 값을 가져온다.
			// next() : 데이터의 row를 지정 -> row데이터를 가져옴 (반환형 : boolean)
			// get자료형(String, Int, Date)("컬럼명" || 인덱스번호)
			// 	getString() : char, varchar2, nchar, nvarchar2 자료형을 가져올 때
			//	getInt()/getDouble() : number 자료형을 가져올 때
			//	getDate()/getTimeStamp() : date, timestamp 자료형을 가져올 때 
//			rs.next(); //첫 번째 row를 지칭
//			while(rs.next()) {
//				String memberId = rs.getString("MEMBER_ID"); //컬럼명 대소문자 구분x
//				String memberPwd = rs.getString("MEMBER_PWD");
//				int age = rs.getInt("AGE");
//				Date enrollDate = rs.getDate("ENROLL_DATE");
//				System.out.println(memberId + " " + memberPwd + " " + age + " " + enrollDate);
//			} //모든 데이터 가져옴
			// 커밋 안하면 데이터 안나옴
//			rs.next(); //두 번째 row를 지칭
//			memberId = rs.getString("MEMBER_ID"); //컬럼명 대소문자 구분x
//			memberPwd = rs.getString("MEMBER_PWD");
//			age = rs.getInt("AGE");
//			enrollDate = rs.getDate("ENROLL_DATE");
//			System.out.println(memberId + " " + memberPwd + " " + age + " " + enrollDate);
			
			//DB의 row를 가져왔을 때 자바에서는 클래스로 저장해서 관리
			List members = new ArrayList();
			while(rs.next()) {
				members.add(new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_PWD"), rs.getString("MEMBER_NAME"),
						rs.getString("GENDER"), rs.getInt("AGE"), rs.getString("EMAIL"), rs.getString("PHONE"), rs.getString("ADDRESS"),
						rs.getString("HOBBY"), rs.getDate("ENROLL_DATE")));
			}
//			while(rs.next()) {
//				Member m=new Member();
//				m.setMemberId(rs.getString("member_id"));
//				m.setMemberPwd(rs.getString("member_pwd"));
//				m.setMemberName(rs.getString("member_name"));
//				m.setGender(rs.getString("gender"));
//				m.setAge(rs.getInt("age"));
//				m.setEmail(rs.getString("email"));
//				m.setPhone(rs.getString("phone"));
//				m.setAddress(rs.getString("address"));
//				m.setHobby(rs.getString("hobby"));
//				m.setEnrollDate(rs.getDate("enroll_date"));
//				members.add(m);
//			} //저장하기(2)
			
//			for(int i = 0; i < members.size(); i++) {
//				System.out.println(members.get(i));
//			}
			members.forEach((m) -> System.out.println(m)); //위와 같은 코드
			
			
			//DML구문 실행하기
			//insert, update, delete문
			//트렌젝션 처리를 해줘야한다.
			//1. sql문 작성 -> 리터럴 형태에 맞춰서 작성
			sql = "INSERT INTO MEMBER VALUES('inhoru', 'inhoru', '최인호',"
					+ "'M', 26, 'inhoru@inhoru.com',"
					+ "'0101234145', '금천구', '영화감상,애니감상,코딩', SYSDATE)";
			// "+"은 자동으로 해줌
			int result = stmt.executeUpdate(sql); //오라클 테이블에 자동 저장
			//트렌젝션 구문으로 처리하기
			if(result > 0) conn.commit();
			else conn.rollback();
			
			System.out.println(result);
			
			//5. 생성한 객체를 반드시 반환해줘야 한다.
			// Connection, Statement, [ResultSet](SELECT했을 때만)
			// close() 메소드를 이용해서 반환
			// 생성한 역순으로 반환해줌
//			rs.close();
//			stmt.close();
//			conn.close();			
			
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close(); //초기값 null로 설정되어있음
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
