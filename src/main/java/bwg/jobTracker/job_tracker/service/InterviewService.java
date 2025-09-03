package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.request.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.dto.InterviewDTO;
import bwg.jobTracker.job_tracker.entity.Interview;
import bwg.jobTracker.job_tracker.repository.InterviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class InterviewService {
    private final InterviewRepository interviewRepository;

    public InterviewService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public InterviewDTO add(InterviewCreateRequest request) {
        Interview interview = new Interview();
        interview.setJobApplication(request.getJobApplication());
        interview.setInterviewType(request.getInterviewType());
        interview.setInterviewDate(request.getInterviewDate());
        interview.setNotes(request.getNotes());
        Interview created = this.interviewRepository.save(interview);

        return MapperUtil.toInterviewDTO(created);
    }

    public List<InterviewDTO> findAll() {
        return this.interviewRepository.findAll().stream()
                .map(MapperUtil::toInterviewDTO)
                .toList();
    }

    public List<InterviewDTO> findAllByInterviewDate(String interviewDate) {
        try {
            LocalDate convertedDate = LocalDate.parse(interviewDate);

            return this.interviewRepository.findAllByInterviewDate(convertedDate).stream()
                    .map(MapperUtil::toInterviewDTO)
                    .toList();
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "only YYYY-MM-DD dates are supported",
                    ex
            );
        }
    }

    public InterviewDTO findById(Long id) {
        Interview existing = this.interviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No interview found for id = " + id
                ));

        return MapperUtil.toInterviewDTO(existing);
    }
}
