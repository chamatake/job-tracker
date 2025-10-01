package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.BaseTest;
import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.TestDataUtil;
import bwg.jobTracker.job_tracker.dto.ApplicationStatusDTO;
import bwg.jobTracker.job_tracker.dto.JobApplicationDTO;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationCreateRequest;
import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest extends BaseTest {
    @Mock
    private JobApplicationRepository repository;
    private JobApplicationService jobApplicationService;

    @BeforeEach
    public void setup() {
        jobApplicationService = new JobApplicationService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        JobPosting requestPosting = makeJobPostingDummy(
                new Company(COMPANY_NAME),
                new ReferralSource(REFERRAL_NAME_GANDALF, ReferralSourceType.INTERNAL),
                OfficeSituation.REMOTE
        );
        JobApplicationCreateRequest request = new JobApplicationCreateRequest(
                requestPosting, RESUME_FILENAME, COVER_LETTER_FILENAME);
        JobApplication added = new JobApplication();

        ApplicationStatus currentStatus = TestDataUtil.makeApplicationStatus(ApplicationStatusType.INTERVIEWING, added, null);
        Set<ApplicationStatus> statuses = TestDataUtil.makeApplicationStatusSet(added, 3);
        statuses.add(currentStatus);

        added.setId(4445L);
        added.setJobPosting(requestPosting);
        added.setApplicationStatuses(statuses);
        added.setCurrentStatusType(currentStatus.getApplicationStatusType());
        added.setAppliedDate(DATE_PI);
        added.setResumeFilename(RESUME_FILENAME);
        added.setCoverLetterFilename(COVER_LETTER_FILENAME);

        when(repository.save(any(JobApplication.class))).thenReturn(added);

        JobApplicationDTO actual = jobApplicationService.add(request);

        assertNotNull(actual.id());
        assertEquals(MapperUtil.toJobPostingDTO(requestPosting), actual.jobPosting());

        Set<ApplicationStatusDTO> actualStatuses = actual.applicationStatuses();
        for(ApplicationStatus expectedStatus : statuses) {
            assertTrue(actualStatuses.contains(MapperUtil.toApplicationStatusDTO(expectedStatus)));
        }

        assertEquals(currentStatus.getApplicationStatusType(), actual.currentStatusType());
        assertEquals(DATE_PI, actual.appliedDate());
        assertEquals(RESUME_FILENAME, actual.resumeFilename());
        assertEquals(COVER_LETTER_FILENAME, actual.coverLetterFilename());
    }
}