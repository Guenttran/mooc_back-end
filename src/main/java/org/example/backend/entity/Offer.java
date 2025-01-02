package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "contract_from")
    private LocalDate contractFrom;

    @Column(name = "contract_to")
    private LocalDate contractTo;

    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    private String status;

    private String note;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private Register register;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee approve;

}
