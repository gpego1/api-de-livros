package io.github.cursospringboot.libraryapi.model;
import io.github.cursospringboot.libraryapi.converter.RolesConverter;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
public class User {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @JdbcTypeCode(Types.BINARY)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "email", length = 600, nullable = false)
    private String email;

    @Column(name = "senha", length = 300, nullable = false)
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", columnDefinition = "VARCHAR(255)")
    private List<String> roles;
}
