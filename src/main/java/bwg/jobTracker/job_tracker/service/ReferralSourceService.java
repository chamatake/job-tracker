package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.ReferralSourceCreateRequest;
import bwg.jobTracker.job_tracker.dto.ReferralSourceDTO;
import bwg.jobTracker.job_tracker.entity.ReferralSource;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.repository.ReferralSourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferralSourceService {
    private final ReferralSourceRepository referralSourceRepository;

    public ReferralSourceService(ReferralSourceRepository repository) {
        this.referralSourceRepository = repository;
    }

    public ReferralSourceDTO add(ReferralSourceCreateRequest request) {
        ReferralSource referralSource = new ReferralSource();
        referralSource.setReferralName(request.getName());
        referralSource.setReferralSourceType(request.getReferralSourceType());
        ReferralSource added = this.referralSourceRepository.save(referralSource);

        return MapperUtil.toReferralSourceDTO(added);
    }

    public List<ReferralSourceDTO> findAll() {
        return this.referralSourceRepository.findAll().stream()
                .map(MapperUtil::toReferralSourceDTO)
                .toList();
    }

    public List<ReferralSourceDTO> findAllBySourceType(ReferralSourceType type) {
        return this.referralSourceRepository.findAllByReferralSourceType(type)
                .stream()
                .map(MapperUtil::toReferralSourceDTO)
                .toList();
    }
}
