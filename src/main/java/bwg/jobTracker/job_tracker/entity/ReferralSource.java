package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "referral_source")
@NoArgsConstructor
@Getter
@Setter
public class ReferralSource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "referral_source_type")
    private ReferralSourceType sourceType;

}
