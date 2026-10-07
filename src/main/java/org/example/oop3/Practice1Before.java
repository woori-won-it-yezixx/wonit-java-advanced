//package org.example.oop3;
//
///**
// * Practice1Before: JAVA 심화 문법 실습.
// *
// * record          : 계좌 데이터를 묶는다.
// * Optional        : 조회 결과가 없을 수 있음을 표현한다.
// * Custom Exception: 실패 이유를 업무 이름으로 표현한다.
// * Lambda / Stream : 목록을 짧고 읽기 좋게 처리한다.
// * try-with-resources: 연결을 자동으로 닫는다.
// */
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class Practice1Before {
//    static Optional<Account> findAccount(String accountNo) throws SQLException {
//        String sql = "SELECT account_no, account_type, balance, status "
//                + "FROM account WHERE account_no = ?";
//
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, accountNo);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (!rs.next()) {
//                    return Optional.empty();
//                }
//
//                return Optional.of(new Account(
//                        rs.getString("account_no"),
//                        rs.getString("account_type"),
//                        rs.getLong("balance"),
//                        rs.getString("status")));
//            }
//        }
//    }
//
//    static boolean withdraw(Account a, long amount) {
//        if (a.status().equals("지급정지")) throw new AccountFrozenException("지급정지된 계좌입니다.");              // (다) AccountFrozenException
//        if (a.balance() < amount) throw new InsufficientBalanceException("잔액이 부족합니다.");                      // (다) InsufficientBalanceException
//        return true;
//    }
//
//    public static void main(String[] args) {
////        Optional<Account> a = findAccount("1002-345-678901");
////        // lambda   () -> { 실행문 }
////        Account account1 = findAccount("1002-345-678901").orElseThrow(() -> new AccountNotFoundException(
////                " 계좌를 찾을 수 없습니다."));
////
////        Account account2 = findAccount("999-000-00000").orElseThrow(() -> new AccountNotFoundException(
////                " 계좌를 찾을 수 없습니다."));
////        System.out.println(findAccount("1002-345-678901").isEmpty()); // 있는 계좌
////        System.out.println(findAccount("1002-345-678901").isPresent()); // 있는 계좌
////        System.out.println(findAccount("1002-345-678901").orElse(new Account("00000", "없음", 0, "불가"))); // 있는 계좌
////        System.out.println(findAccount("9999-000-000000").isEmpty()); // 없는 계좌
////        System.out.println(findAccount("9999-000-000000").isPresent()); // 없는 계좌
////        System.out.println(findAccount("9999-000-000000").orElse(new Account("00000", "없음", 0, "불가"))); // 없는 계좌
////        System.out.println("없는 계좌 잔액: " + none.balance());
//        List<String> fruits = new ArrayList<>(
//                List.of("apple", "zeus", "donut", "candy")
//        );
//
//        fruits.stream().sorted()
//                .filter(word -> word.contains("a"))
//                .map(word->word.toUpperCase())
//                .forEach(word-> System.out.println(word));
//    }
//}
//
//// 기록 (데이터를 빠르게 실어나르기 위한 자바의 새로운 클래스 문법
//// a.accountNo()  이렇게 메서드를 자동으로 만들어줘서 get을 수행할 수 있다.
//// 한번 넣은 값을 수정 불가
//record Account(String accountNo, String accountType, long balance, String status) { }
//
//class AccountNotFoundException extends RuntimeException {
//    AccountNotFoundException(String message) {
//        super(message);
//    }
//}
//
//class AccountFrozenException extends RuntimeException {
//    AccountFrozenException(String message) {
//        super(message);
//    }
//}
//
//// 출금금액보다 잔액이 적을 때
//class InsufficientBalanceException extends RuntimeException {
//    InsufficientBalanceException(String message) {
//        super(message);
//    }
//}
