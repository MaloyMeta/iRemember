package com.maloy.iremember.entity.user;


import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.client.ClientNote;
import com.maloy.iremember.entity.finance.Transaction;
import com.maloy.iremember.enums.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "App_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 2, max = 100)
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 100)
    @NotBlank
    private String lastName;

    @Size(min = 2, max = 100)
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "linkedManager")
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "ownerNote")
    private List<ClientNote> clientNotes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();
}
