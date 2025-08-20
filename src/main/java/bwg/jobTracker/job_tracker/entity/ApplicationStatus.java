package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "application_status")
@NoArgsConstructor
@Getter
@Setter
public class ApplicationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "application_status_type")
    private ApplicationStatusType jobApplicationStatusType;

    @Column(name = "active_date")
    private LocalDate activeDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private boolean isActive;


    @Version
    @Column(name = "VERSION")
    private Long version;
}
