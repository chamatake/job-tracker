package bwg.jobTracker.job_tracker.dto.request;

import bwg.jobTracker.job_tracker.entity.JobPosting;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class JobApplicationCreateRequest {
    @NonNull private JobPosting jobPosting;
    @NonNull private String resumeFilename;
    private String coverLetterFilename;
}
