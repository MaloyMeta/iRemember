package com.maloy.iremember.entity.user;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.finance.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", unique = true)
    @NotBlank
    @Size(min = 2,max = 100)
    private String companyName;

    @Email
    @NotBlank
    @Column(name = "company_email")
    private String companyEmail;

    @OneToMany(mappedBy = "company")
    List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    private List<Transaction> transactions = new ArrayList<>();;
}
