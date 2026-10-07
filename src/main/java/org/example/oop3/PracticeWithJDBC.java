package org.example.oop3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PracticeWithJDBC {

    public static void main(String[] args) throws SQLException {
        System.out.println("=== 1. 계좌 조회 ===");
        Optional<Account> found = findAccount("1002-345-112233");
        found.ifPresent(account ->
                System.out.println("찾은 계좌: " + account.accountNo()));

        Optional<Account> missing = findAccount("9999-000-000000");
        System.out.println("없는 계좌인가? " + missing.isEmpty());

        System.out.println();
        System.out.println("=== 2. 출금 조건 확인 ===");
        tryWithdraw("1002-345-678901", 100_000L);
        tryWithdraw("1002-345-998877", 600_000L);
        tryWithdraw("1002-345-112233", 100_000L);

        System.out.println();
        System.out.println("=== 3. DB 계좌 목록 가공 ===");
        List<Account> accounts = findAllAccounts();

        List<String> nonNormalAccounts = accounts.stream()
                .filter(account -> !account.status().equals("정상"))
                .map(Account::accountNo)
                .toList();
        System.out.println("정상 아님: " + nonNormalAccounts);

        long savingsBalance = accounts.stream()
                .filter(account -> account.accountType().equals("적금"))
                .mapToLong(Account::balance)
                .sum();
        System.out.println("적금 잔액 합계: " + savingsBalance + "원");
    }

    static Optional<Account> findAccount(String accountNo) throws SQLException {
        String sql = """
                SELECT account_no, account_type, balance, status
                FROM account
                WHERE account_no = ?
                """;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toAccount(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    static List<Account> findAllAccounts() throws SQLException {
        String sql = """
                SELECT account_no, account_type, balance, status
                FROM account
                """;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Account> accounts = new ArrayList<>();

            while (resultSet.next()) {
                accounts.add(toAccount(resultSet));
            }
            return accounts;
        }
    }

    private static Account toAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getString("account_no"),
                resultSet.getString("account_type"),
                resultSet.getLong("balance"),
                resultSet.getString("status"));
    }

    static void tryWithdraw(String accountNo, long amount) throws SQLException {
        try {
            Account account = findAccount(accountNo)
                    .orElseThrow(() -> new AccountNotFoundException(
                            accountNo + " 계좌를 찾을 수 없습니다."));
            long remainingBalance = withdraw(account, amount);
            System.out.println(accountNo + " 출금 성공, 남은 잔액: "
                    + remainingBalance + "원");
        } catch (AccountNotFoundException
                 | AccountFrozenException
                 | InsufficientBalanceException e) {
            System.out.println("출금 실패: " + e.getMessage());
        }
    }

    static long withdraw(Account account, long amount) {
        if (account.status().equals("지급정지")) {
            throw new AccountFrozenException(
                    account.accountNo() + "는 지급정지 계좌라 출금할 수 없습니다.");
        }
        if (account.balance() < amount) {
            throw new InsufficientBalanceException(
                    "잔액이 " + (amount - account.balance()) + "원 부족합니다.");
        }
        return account.balance() - amount;
    }
}

record Account(
        String accountNo,
        String accountType,
        long balance,
        String status
) {
}

class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException(String message) {
        super(message);
    }
}

class AccountFrozenException extends RuntimeException {
    AccountFrozenException(String message) {
        super(message);
    }
}

class InsufficientBalanceException extends RuntimeException {
    InsufficientBalanceException(String message) {
        super(message);
    }
}