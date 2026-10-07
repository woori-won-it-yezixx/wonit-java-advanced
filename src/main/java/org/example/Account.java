package org.example;

import lombok.*;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode // 메모리주소가 아니라 값 각각을 비교하도록 함
@AllArgsConstructor
////@NoArgsConstructor // RequiredArgsConstructor, final과 함께 사용 불가
@RequiredArgsConstructor
@Data
public class Account {
    private int accountId;
    private final String accountNo;
    private String accountType;

    @ToString.Exclude
    private int balance;

}
