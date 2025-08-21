package bwg.jobTracker.job_tracker.dto;

public class CompanyCreateRequest {
    private String name;

    public CompanyCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
