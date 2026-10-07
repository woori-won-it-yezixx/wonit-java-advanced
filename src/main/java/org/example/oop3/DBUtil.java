package org.example.oop3;

import java.sql.*;

// 재사용 가능한 방식으로 코드를 분리해서 사용
public class DBUtil {

    // 1. 무조건 mysql 드라이버는 올라와있어야 합니다.
    // 클래스가 메모리에 올라가면서 무조건 mysql driver가 올라와있는 셈
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. DB접속을 위한 객체를 만들어서 url, id, pw를 넘겨줍니다.
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/bankdb";
        String id = "root";
        String pw = "Wooriwon1!";
        return DriverManager.getConnection(url, id, pw);
    }

    // 5. 자원 반납
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}