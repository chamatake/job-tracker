package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.Technology;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    @ElementCollection(targetClass = Technology.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "job_posting_required_tech",
            joinColumns = @JoinColumn(name = "job_posting_id")
    )
    @Column(name = "required_tech")
    private Set<Technology> requiredTech = new HashSet<>();

    @ElementCollection(targetClass = Technology.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "job_posting_preferred_tech",
            joinColumns = @JoinColumn(name = "job_posting_id")
    )
    @Column(name = "preferred_tech")
    private Set<Technology> preferredTech = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "referral_source_id")
    private ReferralSource referralSource;

    @Version
    @Column(name = "VERSION")
    private Long version;
}
