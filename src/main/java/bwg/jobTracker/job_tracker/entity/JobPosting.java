package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.Technology;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "job_posting")
@NoArgsConstructor
@Getter
@Setter
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NonNull private Company company;


    @Column(name = "title", nullable = false)
    @NonNull private String title;

    @Column(name = "requisition_id")
    private String requisitionId;

    @Column(name = "posting_url")
    private String postingUrl;

    @Column(name = "salary_range_min")
    private Integer salaryRangeMin;

    @Column(name = "salary_range_max")
    private Integer salaryRangeMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "office_situation")
    private OfficeSituation officeSituation;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_tech")
    private Set<Technology> requiredTech;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_tech")
    private Set<Technology> preferredTech;

    @ManyToOne
    @JoinColumn(name = "referral_source_id")
    private ReferralSource referralSource;

    @Version
    @Column(name = "VERSION")
    private Long version;
}
