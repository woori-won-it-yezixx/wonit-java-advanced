package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Account account1 = new Account(1, "123-45","예적금",100000);
        account1.setAccountId(1);
        System.out.println(account1.getAccountId());
        System.out.println(account1.toString());
        System.out.println(account1.hashCode());
        Account account2 = new Account("123-45");
        account2.setAccountId(1);
        System.out.println(account2.toString());
        System.out.println(account2.hashCode());
        System.out.println(account1.equals(account2));
    }
}