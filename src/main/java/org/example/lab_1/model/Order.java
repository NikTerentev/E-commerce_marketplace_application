package org.example.lab_1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders", schema = "public", catalog = "e-commerce") // Имя таблицы в базе данных
@Schema(description = "Сущность заказа")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    @Schema(description = "Идентификатор заказа", example = "1")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "p_id", nullable = false)
    private Product pid;

    @Column(name = "u_id")
    @Schema(description = "Идентификатор пользователя", example = "1")
    private int uid;

    @Column(name = "o_quantity")
    @Schema(description = "Количество", example = "2")
    private int quantity;

    @Column(name = "o_date")
    @Schema(description = "Дата", example = "2024-02-28")
    private String date;
}
