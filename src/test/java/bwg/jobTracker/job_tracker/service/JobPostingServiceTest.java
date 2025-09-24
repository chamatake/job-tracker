package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.TestDataUtil;
import bwg.jobTracker.job_tracker.dto.JobPostingDTO;
import bwg.jobTracker.job_tracker.dto.request.JobPostingCreateRequest;
import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.entity.JobPosting;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.exception.JobPostingNotFoundException;
import bwg.jobTracker.job_tracker.repository.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JobPostingServiceTest {

    @Mock
    private JobPostingRepository repository;
    private JobPostingService jobPostingService;

    @BeforeEach
    void setup() {
        jobPostingService = new JobPostingService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        JobPostingCreateRequest request = TestDataUtil.makeJobPostingRequestWithTech(OfficeSituation.HYBRID, 7, 3);
        JobPosting added = TestDataUtil.makeJobPostingWithTech(OfficeSituation.HYBRID, 7, 3);
        added.setId(new Random().nextLong(1000000));
        added.setRequiredTech(request.getRequiredTech());
        added.setPreferredTech(request.getPreferredTech());

        when(repository.save(any(JobPosting.class))).thenReturn(added);

        JobPostingDTO actual = jobPostingService.add(request);

        assertNotNull(actual.id());
        assertEquals(MapperUtil.toCompanyDTO(request.getCompany()), actual.company());
        assertEquals(request.getTitle(), actual.title());
        assertEquals(request.getRequisitionId(), actual.requisitionId());
        assertEquals(request.getUrl(), actual.url());
        assertEquals(request.getSalaryRangeMin(), actual.salaryRangeMin());
        assertEquals(request.getSalaryRangeMax(), actual.salaryRangeMax());
        assertEquals(request.getOfficeSituation(), actual.officeSituation());
        assertTrue(actual.requiredTech().containsAll(request.getRequiredTech()));
        assertTrue(actual.preferredTech().containsAll(request.getPreferredTech()));
        assertEquals(MapperUtil.toReferralSourceDTO(request.getReferralSource()), actual.referralSource());
    }

    @Test
    public void testFindAll_happyPath() {
        JobPosting postingHybrid = TestDataUtil.makeJobPostingNoTech(OfficeSituation.HYBRID);
        postingHybrid.setId(12345L);
        JobPosting postingRemote = TestDataUtil.makeJobPostingNoTech(OfficeSituation.REMOTE);
        List<JobPosting> existing = List.of(postingRemote, postingHybrid);

        when(repository.findAll()).thenReturn(existing);

        List<JobPostingDTO> actual = jobPostingService.findAll();

        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toJobPostingDTO(postingHybrid)));
        assertTrue(actual.contains(MapperUtil.toJobPostingDTO(postingRemote)));
    }

    @Test
    public void testFindAllByCompany_happyPath() {
        final Company searchCompany = new Company("junky junk");
        JobPosting postingHybrid = TestDataUtil.makeJobPostingNoTech(OfficeSituation.HYBRID);
        postingHybrid.setId(12345L);
        postingHybrid.setCompany(searchCompany);
        JobPosting postingRemote2 = TestDataUtil.makeJobPostingNoTech(OfficeSituation.REMOTE);
        postingRemote2.setCompany(searchCompany);
        List<JobPosting> existing = List.of(postingRemote2, postingHybrid);

        when(repository.findAllByCompany(any(Company.class))).thenReturn(existing);

        List<JobPostingDTO> actual = jobPostingService.findAllByCompany(searchCompany);

        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toJobPostingDTO(postingHybrid)));
        assertTrue(actual.contains(MapperUtil.toJobPostingDTO(postingRemote2)));
    }

    @Test
    public void testFindAllByCompanyName_happyPath() {
        final String searchName = "junky junk";
        JobPosting postingHybrid = TestDataUtil.makeJobPostingNoTech(OfficeSituation.HYBRID);
        postingHybrid.setId(12345L);
        postingHybrid.getCompany().setCompanyName(searchName);
        JobPosting postingRemote2 = TestDataUtil.makeJobPostingNoTech(OfficeSituation.REMOTE);
        postingRemote2.getCompany().setCompanyName(searchName);
        List<JobPosting> existing = List.of(postingRemote2, postingHybrid);

        when(repository.findAllByCompany_companyName(searchName)).thenReturn(existing);

        List<JobPostingDTO> actual = jobPostingService.findAllByCompanyName(searchName);

        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toJobPostingDTO(postingHybrid)));
        assertTrue(actual.contains(MapperUtil.toJobPostingDTO(postingRemote2)));
    }

    @Test
    public void testFindAllById_notFound_happyPath() {
        when(repository.findById(anyLong())).thenThrow(JobPostingNotFoundException.class);

        assertThrows(JobPostingNotFoundException.class, () -> jobPostingService.findById(123L));
    }

    @Test
    public void testFindById_happyPath() {
        JobPosting existing = TestDataUtil.makeJobPostingNoTech(OfficeSituation.ONSITE);
        Long existingId = existing.getId();

        when(repository.findById(existingId)).thenReturn(Optional.of(existing));

        JobPostingDTO actual = jobPostingService.findById(existingId);

        assertEquals(existing.getId(), actual.id());
        assertEquals(MapperUtil.toCompanyDTO(existing.getCompany()), actual.company());
        assertEquals(existing.getTitle(), actual.title());
        assertEquals(existing.getRequisitionId(), actual.requisitionId());
        assertEquals(existing.getPostingUrl(), actual.url());
        assertEquals(existing.getSalaryRangeMin(), actual.salaryRangeMin());
        assertEquals(existing.getSalaryRangeMax(), actual.salaryRangeMax());
        assertEquals(existing.getOfficeSituation(), actual.officeSituation());
        assertEquals(MapperUtil.toReferralSourceDTO(existing.getReferralSource()), actual.referralSource());
        assertTrue(actual.requiredTech().containsAll(existing.getRequiredTech()));
        assertTrue(actual.preferredTech().containsAll(existing.getPreferredTech()));
    }
}

