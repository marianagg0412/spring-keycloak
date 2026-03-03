-- Clean tables and reset identities safely
TRUNCATE TABLE evidences RESTART IDENTITY CASCADE;
TRUNCATE TABLE victims RESTART IDENTITY CASCADE;
TRUNCATE TABLE cases RESTART IDENTITY CASCADE;

-- Insert cases (IDs will start at 1 automatically)
INSERT INTO cases (title, description, investigation_start_date, investigation_status, detective_name)
VALUES
    ('Homicide A', 'Investigation of suspicious death', '2025-10-01 10:00:00', 'OPEN', 'Detective Holmes'),
    ('Missing Person B', 'Search for missing person', '2025-12-15 09:00:00', 'CLOSED', 'Detective Marple');

-- Insert victims (now safe because cases 1 and 2 exist)
INSERT INTO victims (name, surname, date_of_death, cause_of_death, social_status, case_id)
VALUES
    ('John', 'Doe', '2025-09-30 23:00:00', 'Blunt force trauma', 'Citizen', 1),
    ('Jane', 'Smith', '2025-12-10 18:30:00', 'Unknown', 'Resident', 2);

-- Insert evidences
INSERT INTO evidences (type, description, location_found, date_collected, case_id)
VALUES
    ('Weapon', 'Bloody knife recovered from kitchen', 'Kitchen floor', '2025-10-01 11:15:00', 1),
    ('Fingerprint', 'Partial fingerprint on door handle', 'Main entrance door', '2025-10-01 12:00:00', 1),
    ('CCTV Footage', 'Surveillance video showing last known location', 'Shopping mall parking', '2025-12-15 10:30:00', 2),
    ('Personal Item', 'Abandoned backpack with ID documents', 'Bus station', '2025-12-16 08:45:00', 2);