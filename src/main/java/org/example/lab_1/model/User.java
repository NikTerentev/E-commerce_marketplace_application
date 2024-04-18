package org.example.lab_1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "e-commerce")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность пользователя")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    @Schema(description = "Идентификатор", example = "1")
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    @Schema(description = "Имя", example = "Антон")
    private String name;

    @Column(name = "nickname", nullable = false, length = 250)
    @Schema(description = "Никнейм", example="cool_user")
    private String nickname;

    @Column(name = "status", nullable = false)
    @Schema(description = "Статус пользователя", example = "ONLINE")
    private String status;

    @Column(name = "email", nullable = false, length = 250)
    @Schema(description = "Почта", example = "test@mail.ru")
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    @Schema(description = "Пароль", example = "12345")
    private String password;

    @Column(name = "token", length = 250)
    @Schema(description = "Токен", example = "12345")
    private String token;
}
