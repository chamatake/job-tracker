package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.Technology;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @OneToMany
    @JoinColumn(name = "company_id")
    private Company company;


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "requisition_id")
    private String requisitionId;

    @Column(name = "url")
    private String url;

    @Column(name = "salary_range_min")
    private Long salaryRangeMin;

    @Column(name = "salary_range_max")
    private Long salaryRangeMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "office_situation")
    private OfficeSituation officeSituation;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_tech")
    private Set<Technology> requiredTech;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_tech")
    private Set<Technology> preferredTech;


    @Version
    @Column(name = "VERSION")
    private Long version;
}
