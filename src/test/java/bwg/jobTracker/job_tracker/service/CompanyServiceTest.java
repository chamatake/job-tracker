package bwg.jobTracker.job_tracker.service;


import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.dto.request.CompanyCreateRequest;
import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.exception.CompanyNotFoundException;
import bwg.jobTracker.job_tracker.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @Mock
    private CompanyRepository repository;
    private CompanyService companyService;

    private final String COMPANY_NAME = "Wayne Enterprises";

    @BeforeEach
    public void setup() {
        companyService = new CompanyService(repository);
    }

    @Test
    public void testAdd_happyPath() {
        CompanyCreateRequest request = new CompanyCreateRequest(COMPANY_NAME);
        Company added = new Company(COMPANY_NAME);
        added.setId(new Random().nextLong(12345));

        when(repository.save(any(Company.class))).thenReturn(added);

        CompanyDTO actual = companyService.add(request);
        assertNotNull(actual.id());
        assertEquals(request.getName(), actual.name());
    }

    @Test
    public void testFindById_happyPath() {
        Long id = 12354L;
        Company existing = new Company(id, COMPANY_NAME);

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        CompanyDTO actual = companyService.findById(id);

        assertEquals(id, actual.id());
        assertEquals(existing.getCompanyName(), actual.name());
    }

    @Test
    public void testFindById_notFound_happyPath() {
        when(repository.findById(anyLong())).thenThrow(CompanyNotFoundException.class);
        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(1234L));
    }

    @Test
    public void testFindByCompanyName_happyPath() {
        Company existing = new Company(4444L, COMPANY_NAME);
        when(repository.findByCompanyName(anyString())).thenReturn(Optional.of(existing));

        CompanyDTO actual = companyService.findByCompanyName(COMPANY_NAME);

        assertEquals(existing.getId(), actual.id());
        assertEquals(existing.getCompanyName(), actual.name());
    }

    @Test
    public void testFindByCompanyName_notFound_happyPath() {
        when(repository.findByCompanyName(anyString())).thenThrow(CompanyNotFoundException.class);
        assertThrows(CompanyNotFoundException.class, () -> companyService.findByCompanyName(COMPANY_NAME));
    }

    @Test
    public void testFindAll_happyPath() {
        Company company1 = new Company(12345L, COMPANY_NAME);
        Company company2 = new Company(54321L, "Lex Corp");
        List<Company> existing = List.of(company2, company1);

        when(repository.findAll()).thenReturn(existing);

        List<CompanyDTO> actual = companyService.findAll();

        assertEquals(existing.size(), actual.size());
        assertTrue(actual.contains(MapperUtil.toCompanyDTO(company1)));
        assertTrue(actual.contains(MapperUtil.toCompanyDTO(company2)));
    }
}