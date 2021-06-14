package ru.netology.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEntity {
    String credit_id;
    String payment_id;
    String id;
    String created;
}
