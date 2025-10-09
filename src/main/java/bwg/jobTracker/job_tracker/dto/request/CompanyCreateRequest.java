package bwg.jobTracker.job_tracker.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CompanyCreateRequest {
    @NonNull private String name;
}
