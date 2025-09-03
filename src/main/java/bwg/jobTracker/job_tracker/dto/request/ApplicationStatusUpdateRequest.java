package bwg.jobTracker.job_tracker.dto.request;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ApplicationStatusUpdateRequest {
    @NonNull private ApplicationStatusType applicationStatusType;
}
