package bwg.jobTracker.job_tracker.dto.request;

import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.Technology;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class JobPostingCreateRequest {
    @NonNull private Company company;
    @NonNull private String title;
    private String requisitionId;
    private String url;
    private Integer salaryRangeMin;
    private Integer salaryRangeMax;
    @NonNull OfficeSituation officeSituation;
    private Set<Technology> requiredTech;
    private Set<Technology> preferredTech;

    public JobPostingCreateRequest(Company company, String title, String url, OfficeSituation officeSituation) {
        this.company = company;
        this.title = title;
        this.url = url;
        this.officeSituation = officeSituation;
    }
}
