package bwg.jobTracker.job_tracker.dto.request;

import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.enums.InterviewType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class InterviewCreateRequest {
    @NonNull private JobApplication jobApplication;
    @NonNull private InterviewType interviewType;
    @NonNull private LocalDate interviewDate;
    private String notes;
}
