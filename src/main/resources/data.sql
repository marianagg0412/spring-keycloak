-- Seed initial cases (IDs let the DB assign values)
INSERT INTO cases (title, description, investigation_start_date, investigation_status, detective_name)
VALUES ('Homicide A', 'Investigation of suspicious death', '2025-10-01 10:00:00', 'OPEN', 'Detective Holmes');

INSERT INTO cases (title, description, investigation_start_date, investigation_status, detective_name)
VALUES ('Missing Person B', 'Search for missing person', '2025-12-15 09:00:00', 'CLOSED', 'Detective Marple');

-- Seed initial victims (associate with case IDs 1 and 2 expecting insertion order)
INSERT INTO victims (name, surname, date_of_death, cause_of_death, social_status, case_id)
VALUES ('John', 'Doe', '2025-09-30 23:00:00', 'Blunt force trauma', 'Citizen', 1);

INSERT INTO victims (name, surname, date_of_death, cause_of_death, social_status, case_id)
VALUES ('Jane', 'Smith', '2025-12-10 18:30:00', 'Unknown', 'Resident', 2);

-- Seed initial evidences (associate with case IDs 1 and 2)

INSERT INTO evidences (type, description, location_found, date_collected, case_id)
VALUES ('Weapon', 'Bloody knife recovered from kitchen', 'Kitchen floor', '2025-10-01 11:15:00', 1);

INSERT INTO evidences (type, description, location_found, date_collected, case_id)
VALUES ('Fingerprint', 'Partial fingerprint on door handle', 'Main entrance door', '2025-10-01 12:00:00', 1);

INSERT INTO evidences (type, description, location_found, date_collected, case_id)
VALUES ('CCTV Footage', 'Surveillance video showing last known location', 'Shopping mall parking', '2025-12-15 10:30:00', 2);

INSERT INTO evidences (type, description, location_found, date_collected, case_id)
VALUES ('Personal Item', 'Abandoned backpack with ID documents', 'Bus station', '2025-12-16 08:45:00', 2);