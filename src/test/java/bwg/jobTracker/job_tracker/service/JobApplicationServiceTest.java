package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.BaseTest;
import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.TestDataUtil;
import bwg.jobTracker.job_tracker.dto.ApplicationStatusDTO;
import bwg.jobTracker.job_tracker.dto.JobApplicationDTO;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationCreateRequest;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationUpdateStatusRequest;
import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.exception.JobApplicationNotFoundException;
import bwg.jobTracker.job_tracker.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        JobPosting requestPosting = makeJobPostingDummyGandalf();
        JobApplicationCreateRequest request = new JobApplicationCreateRequest(
                requestPosting, RESUME_FILENAME, COVER_LETTER_FILENAME);
        JobApplication added = makeJobApplicationDummy(4445L, requestPosting);

        when(repository.save(any(JobApplication.class))).thenReturn(added);

        JobApplicationDTO actual = jobApplicationService.add(request);

        assertNotNull(actual.id());
        assertEquals(MapperUtil.toJobPostingDTO(requestPosting), actual.jobPosting());
        List<ApplicationStatus> statuses = added.getApplicationStatuses();

        List<ApplicationStatusDTO> actualStatuses = actual.applicationStatuses();
        for(ApplicationStatus expectedStatus : statuses) {
            assertTrue(actualStatuses.contains(MapperUtil.toApplicationStatusDTO(expectedStatus)));
        }

        assertEquals(added.getCurrentStatus().getId(), actual.currentStatusId());
        assertEquals(DATE_PI, actual.appliedDate());
        assertEquals(RESUME_FILENAME, actual.resumeFilename());
        assertEquals(COVER_LETTER_FILENAME, actual.coverLetterFilename());
        assertEquals(MapperUtil.toJobPostingDTO(requestPosting), actual.jobPosting());
    }

    @Test
    public void testFindAll_happyPath() {
        JobPosting postingOne = makeJobPostingDummyGandalf();
        JobPosting postingTwo = makeJobPostingDummy(
                new Company(COMPANY_NAME_JUNK),
                new ReferralSource(REFERRAL_NAME_BORING, ReferralSourceType.INTERNAL),
                OfficeSituation.HYBRID
        );
        JobApplication one = makeJobApplicationDummy(33545L, postingOne);
        JobApplication two = makeJobApplicationDummy(7777L, postingTwo);
        List<JobApplication> existing = new ArrayList<>();
        existing.add(one);
        existing.add(two);

        when(repository.findAll()).thenReturn(existing);

        List<JobApplicationDTO> actual = jobApplicationService.findAll();

        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toJobApplicationDTO(one)));
        assertTrue(actual.contains(MapperUtil.toJobApplicationDTO(two)));
    }

    @Test
    public void testFindById_happyPath() {
        JobPosting posting = makeJobPostingDummyGandalf();
        JobApplication existing = makeJobApplicationDummy(12345L, posting);

        when(repository.findById(anyLong())).thenReturn(Optional.of(existing));

        JobApplicationDTO actual = jobApplicationService.findById(12345L);

        assertEquals(existing.getId(), actual.id());
        assertEquals(MapperUtil.toJobPostingDTO(posting), actual.jobPosting());
        assertEquals(existing.getApplicationStatuses().size(), actual.applicationStatuses().size());
        existing.getApplicationStatuses()
                .forEach(existingStatus -> assertTrue(actual.applicationStatuses()
                        .contains(MapperUtil.toApplicationStatusDTO(existingStatus))));
        assertEquals(existing.getCurrentStatus().getId(), actual.currentStatusId());
        assertEquals(existing.getAppliedDate(), actual.appliedDate());
        assertEquals(existing.getResumeFilename(), actual.resumeFilename());
        assertEquals(existing.getCoverLetterFilename(), actual.coverLetterFilename());
    }

    @Test
    public void testFindById_notFound_happyPath() {
        when(repository.findById(anyLong())).thenThrow(JobApplicationNotFoundException.class);
        assertThrows(JobApplicationNotFoundException.class, () -> jobApplicationService.findById(112233L));
    }

    @Test
    public void testUpdateStatusById_happyPath() {
        JobPosting requestPosting = makeJobPostingDummyGandalf();
        JobApplicationUpdateStatusRequest updateRequest = new JobApplicationUpdateStatusRequest(JOB_APPLICATION_ID, ApplicationStatusType.INTERVIEWING);
        JobApplication existing = makeJobApplicationDummy(JOB_APPLICATION_ID, requestPosting);
        List<ApplicationStatus> existingStatuses = new ArrayList<>();
        existingStatuses.add(TestDataUtil.makeApplicationStatus(ApplicationStatusType.APPLIED, existing, null));
        existing.setApplicationStatuses(existingStatuses);

        JobApplication updated = makeJobApplicationDummyNoStatuses(JOB_APPLICATION_ID, requestPosting);
        ApplicationStatus first = new ApplicationStatus(90L, updated, ApplicationStatusType.APPLIED, DATE_PI);
        ApplicationStatus second = new ApplicationStatus(91L, updated, ApplicationStatusType.INTERVIEWING, LocalDate.now());

        updated.setApplicationStatuses(List.of(first, second));
        updated.setCurrentStatus(second);

        when(repository.findById(JOB_APPLICATION_ID)).thenReturn(Optional.of(existing));
        when(repository.save(any(JobApplication.class))).thenReturn(updated);

        JobApplicationDTO actual = jobApplicationService.updateStatusById(updateRequest);
        assertEquals(91L, actual.currentStatusId());
        assertEquals(2, actual.applicationStatuses().size());
        assertEquals(updateRequest.getJobApplicationId(), actual.id());
    }

    @Test
    public void testUpdateStatusById_noop_happyPath() {
        JobPosting requestPosting = makeJobPostingDummyGandalf();
        JobApplicationUpdateStatusRequest updateRequest = new JobApplicationUpdateStatusRequest(JOB_APPLICATION_ID, ApplicationStatusType.APPLIED);
        JobApplication existing = makeJobApplicationDummy(JOB_APPLICATION_ID, requestPosting);
        List<ApplicationStatus> existingStatuses = new ArrayList<>();
        existingStatuses.add(new ApplicationStatus(90L, existing, ApplicationStatusType.APPLIED, DATE_PI));
        existing.setApplicationStatuses(existingStatuses);

        JobApplication updated = makeJobApplicationDummyNoStatuses(JOB_APPLICATION_ID, requestPosting);
        ApplicationStatus first = new ApplicationStatus(90L, updated, ApplicationStatusType.APPLIED, DATE_PI);

        updated.setApplicationStatuses(List.of(first));
        updated.setCurrentStatus(first);

        when(repository.findById(JOB_APPLICATION_ID)).thenReturn(Optional.of(existing));
        when(repository.save(any(JobApplication.class))).thenReturn(updated);

        JobApplicationDTO actual = jobApplicationService.updateStatusById(updateRequest);
        assertEquals(90L, actual.currentStatusId());
        assertEquals(1, actual.applicationStatuses().size());
        assertEquals(updateRequest.getJobApplicationId(), actual.id());
    }

    @Test
    public void testUpdateStatusById_notFound_happyPath() {
        JobApplicationUpdateStatusRequest updateRequest = new JobApplicationUpdateStatusRequest(JOB_APPLICATION_ID, ApplicationStatusType.INTERVIEWING);

        when(repository.findById(anyLong())).thenThrow(JobApplicationNotFoundException.class);
        assertThrows(JobApplicationNotFoundException.class, () -> jobApplicationService.updateStatusById(updateRequest));
        verify(repository, times(0)).save(any(JobApplication.class));
    }


}