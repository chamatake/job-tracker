INSERT INTO company(id, company_name) VALUES (1, 'Wayne Enterprises');
INSERT INTO company(id, company_name) VALUES (2, 'Shinra Inc.');
INSERT INTO referral_source(id, referral_name, referral_source_type)
    VALUES(1, 'Bruce Wayne', 'INTERNAL');
INSERT INTO referral_source(id, referral_name, referral_source_type)
    VALUES(2, 'President Shinra', 'INTERNAL');
INSERT INTO referral_source(id, referral_name, referral_source_type)
    VALUES(3, 'Shinra The App', 'APP');
INSERT INTO job_posting(id, company_id, title, requisition_id, posting_url, salary_range_min, salary_range_max, office_situation, required_tech, preferred_tech, referral_source_id, `VERSION`)
    VALUES(1, 2, 'Company President - Interim', 'RSHIN-98874', 'https://shinrapowerco.jp/jobs/RSHIN-98874', null, null, 'ONSITE', null, null, 3, 1);
INSERT INTO job_posting(id, company_id, title, requisition_id, posting_url, salary_range_min, salary_range_max, office_situation, required_tech, preferred_tech, referral_source_id, `VERSION`)
    VALUES(2, 1, 'taxes guy', 'JK-000tax000', 'https://wayne.com/careers/taxes-guy', 10000, 150000, 'REMOTE', 'Quickbooks, jk not quickbooks', null, 1, 1);

INSERT INTO job_application(id, job_posting_id, current_status_id, applied_date, resume_filename, cover_letter_filename, `VERSION`)
    VALUES(1, 1, null, '1997-01-31', 'my-resume.pdf', null, 1);
INSERT INTO job_application(id, job_posting_id, current_status_id, applied_date, resume_filename, cover_letter_filename, `VERSION`)
    VALUES(2, 2, null, '2024-02-02', 'I-do-taxes.docs', null, 1);

INSERT INTO application_status(id, job_application_id, application_status_type, active_date, inactive_date, `VERSION`)
    VALUES(1, 1, 'APPLIED', '1997-02-14', '1997-02-20', 1);
INSERT INTO application_status(id, job_application_id, application_status_type, active_date, inactive_date, `VERSION`)
    VALUES(2, 1, 'SCREENING', '1997-02-20', '1997-02-22', 1);
INSERT INTO application_status(id, job_application_id, application_status_type, active_date, inactive_date, `VERSION`)
    VALUES(3, 1, 'INTERVIEWING', '1997-02-22', null, 1);
INSERT INTO application_status(id, job_application_id, application_status_type, active_date, inactive_date, `VERSION`)
    VALUES(4, 2, 'APPLIED', '2024-02-05', null, 1);

UPDATE job_application SET current_status_id = 3 WHERE id = 1;
UPDATE job_application SET current_status_id = 4 WHERE id = 2;

INSERT INTO interview(id, job_application_id, interview_type, interview_date, notes, `VERSION`)
    VALUES(1, 1, 'INITIAL_SCREEN', '1997-02-22', 'It was a good interview. President Shinra seems like a completely trustworthy dude.', 1);
INSERT INTO interview(id, job_application_id, interview_type, interview_date, notes, `VERSION`)
    VALUES(2, 1, 'TECHNICAL_INTERVIEW', '1997-02-25', 'They had me build some machines for a hands-on interview. Totally not weird.', 1);
INSERT INTO interview(id, job_application_id, interview_type, interview_date, notes, `VERSION`)
    VALUES(3, 1, 'MIDDLE_INTERVIEW', '1997-02-28', null, 1);
INSERT INTO interview(id, job_application_id, interview_type, interview_date, notes, `VERSION`)
    VALUES(4, 1, 'MIDDLE_INTERVIEW', '1997-03-01', null, 1);