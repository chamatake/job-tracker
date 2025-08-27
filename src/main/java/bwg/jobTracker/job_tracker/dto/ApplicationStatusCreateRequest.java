package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ApplicationStatusCreateRequest {
    @NonNull private ApplicationStatusType applicationStatusType;
    private LocalDate activeDate;
}
