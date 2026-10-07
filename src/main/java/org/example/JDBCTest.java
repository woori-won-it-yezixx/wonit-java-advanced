package org.example;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1. JDBC 통역기(드라이버)를 받아옵니다.
        Class.forName("com.mysql.cj.jdbc.Driver");


        // 2. DB접속을 위한 객체를 만들어서 url, id, pw를 넘겨줍니다.
        String url = "jdbc:mysql://localhost:3306/wonit";
        String id = "root";
        String pw = "Wooriwon1!";
        Connection conn = DriverManager.getConnection(url, id, pw);

        // 3. 문장 실행해줄 객체
        Statement stmt = conn.createStatement();

        // 3-1. executeUpdate - Read를 제외한 모든 sql 명령어를 실행
        // Create
        String sql2 = "INSERT INTO dept VALUES (99, 'OPERATION', 'SEOUL')";

        // Update
        // String sql2 = "UPDATE dept SET dname='OPERATION2' WHERE deptno=99";


        // DELETE
//            String sql2 = "DELETE FROM dept WHERE deptno=99";

        // 그 결과로 실제 영향을 끼친 row의 수를 리턴
        int rs2 = stmt.executeUpdate(sql2);
        System.out.println(rs2);



        // 3-2. excuteQuery() : select문(get method)
        String sql = "select * from dept";

        // 그 결과로 실제 영향을 받은 값 또는 영향을 미친 행의 수를 리턴해주는 ResultSet 객체
        // 실제 Result를 가지고 있는 객체
        /* 1. 커서(포인터)를 이동시키는 기능 next()
         * 2. 데이터를 리턴하는 기능 : getXxx(컬럼의 번호 or 컬럼 이름)
         * 				정수로 된 자료형의 컬럼 반환 : getInt(컬럼번호 or 컬럼명)
         * 				문자열로 된 자료형 컬럼 반환 : getString(컬럼번호 or 컬럼명)
         */
        // executeQuery - Read를 실행
        // 그 결과로 SELECT된 전체 행을 가져 리턴
        ResultSet rs = stmt.executeQuery(sql); // Get

        // 4. DB에서 가져온 값으로 실제 동작을 수행
        while (rs.next()) {
            System.out.println(rs.getInt(1)); // 컬럼 순번(SQL의 규칙대로 1부터 시작) 정수형으로 된 자료 컬럼을 출력
            System.out.println(rs.getString("dname")); // 컬럼명으로 문자열로 된 자료 컬럼을 출력
        }



        // 5. 자원 반납
        rs.close();
        stmt.close();
        conn.close();
    }
}

