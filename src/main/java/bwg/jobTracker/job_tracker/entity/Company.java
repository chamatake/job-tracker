package bwg.jobTracker.job_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_name", nullable = false)
    @NonNull private String companyName;

}
