package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "referral_source")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ReferralSource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "referral_name")
    @NonNull private String referralName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "referral_source_type")
    @NonNull private ReferralSourceType referralSourceType;

}
