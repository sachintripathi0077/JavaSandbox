package com.dev.accounts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.data.annotation.Reference;

@Data
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

        @Column(name = "customer_id")
        private Long customerId;

        @Column(name = "account_number")
        @Id
        private Long accountNumber;

        @Column(name = "account_type")
        private String accountType;

        @Column(name = "branch_address")
        private String branchAddress;

        @Column(name = "communication_sw")
        private Boolean communicationSw;
}
