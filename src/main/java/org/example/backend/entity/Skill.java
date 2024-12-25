package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "skills")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skill extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "skill_name")
    private String skillName;

    @Column(length = Integer.MAX_VALUE)
    private String description;
}
