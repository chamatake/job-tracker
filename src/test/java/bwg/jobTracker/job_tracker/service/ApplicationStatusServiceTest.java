package bwg.jobTracker.job_tracker.service;


import bwg.jobTracker.job_tracker.BaseTest;
import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.ApplicationStatusDTO;
import bwg.jobTracker.job_tracker.dto.request.ApplicationStatusCreateRequest;
import bwg.jobTracker.job_tracker.entity.ApplicationStatus;
import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.entity.JobPosting;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.repository.ApplicationStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationStatusServiceTest extends BaseTest {
    @Mock
    private ApplicationStatusRepository repository;
    private ApplicationStatusService applicationStatusService;

    @BeforeEach
    public void setup() {
        applicationStatusService = new ApplicationStatusService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        ApplicationStatusCreateRequest request = new ApplicationStatusCreateRequest(ApplicationStatusType.APPLIED);
        request.setActiveDate(DATE_e);
        JobPosting parentPosting = makeJobPostingDummyGandalf();
        JobApplication parent = new JobApplication();
        parent.setId(JOB_APPLICATION_ID);
        parent.setJobPosting(parentPosting);
        ApplicationStatus added = new ApplicationStatus(parent, ApplicationStatusType.APPLIED);
        added.setId(APPLICATION_STATUS_ID);
        added.setActiveDate(DATE_e);

        when(repository.save(any(ApplicationStatus.class))).thenReturn(added);

        ApplicationStatusDTO actual = applicationStatusService.add(request);
        assertNotNull(actual.id());
        assertEquals(ApplicationStatusType.APPLIED, actual.applicationStatusType());
        assertEquals(DATE_e, actual.activeDate());
        assertEquals(JOB_APPLICATION_ID, actual.jobApplicationId());
    }

    @Test
    public void testFindAllByJobApplicationId_happyPath() {
        JobPosting parentPosting = makeJobPostingDummyGandalf();
        JobApplication parent = new JobApplication();
        parent.setId(JOB_APPLICATION_ID);
        parent.setJobPosting(parentPosting);
        ApplicationStatus applied = new ApplicationStatus(parent, ApplicationStatusType.APPLIED);
        ApplicationStatus interviewing = new ApplicationStatus(parent, ApplicationStatusType.INTERVIEWING);
        ApplicationStatus screening = new ApplicationStatus(parent, ApplicationStatusType.SCREENING);
        List<ApplicationStatus> existing = List.of(applied, screening, interviewing);

        when(repository.findAllByJobApplication_Id(anyLong())).thenReturn(existing);

        List<ApplicationStatusDTO> actual = applicationStatusService.findAllByJobApplicationId(JOB_APPLICATION_ID);
        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toApplicationStatusDTO(applied)));
        assertTrue(actual.contains(MapperUtil.toApplicationStatusDTO(interviewing)));
        assertTrue(actual.contains(MapperUtil.toApplicationStatusDTO(screening)));
    }
}