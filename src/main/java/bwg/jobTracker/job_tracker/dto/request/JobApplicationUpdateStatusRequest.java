package bwg.jobTracker.job_tracker.dto.request;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationUpdateStatusRequest {
    private Long jobApplicationId;
    private ApplicationStatusType statusType;
}
