package com.eazybytes.eazyschool.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {
    @Id
    @Column(name = "day")
    private  String day;

    @Column(name = "reason")
    private  String reason;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private  Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }

}
