package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "application_status")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ApplicationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "application_status_type")
    @NonNull private ApplicationStatusType applicationStatusType;

    @Column(name = "active_date")
    private LocalDate activeDate;


    @Version
    @Column(name = "VERSION")
    private Long version;
}
