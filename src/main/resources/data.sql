
INSERT INTO department (name, description)VALUES
    ('HR', 'Human Resources department'),
    ('IT', 'Information Technology department'),
    ('Logistics', 'Logistics department');


INSERT INTO job_position (name, description, requests, responsibilities, department_id)VALUES
    ('Junior HR', 'Junior HR position', 'Bachelor\'s degree in Human Resources or related field', 'Responsibilities for junior HR', 1),
    ('HR Junior', 'HR Junior position', 'Bachelor\'s degree in Human Resources or related field', 'Responsibilities for HR Junior', 2),
    ('IT', 'IT position', 'Bachelor\'s degree in Computer Science or related field', 'Responsibilities for IT', 3);

INSERT INTO `employee` (`first_Name`, `last_Name`, `birth_Date`, `address`, `salary`, `job_position_id`, `department_id`) VALUES
('John', 'Doe', '1990-05-15', '123 Main Street', 22, 1, 1),
('Alice', 'Smith', '1985-08-20', '456 Elm Street', 24, 2, 2),
('Bob', 'Johnson', '1978-03-10', '789 Oak Avenue', 222, 3, 3);

INSERT INTO `attendance`(`data`, `arrival_time`, `departure_time`, `employee_id`, `department_id`)VALUES
('2024-04-16', '09:30:00', '18:00:00', 1, 1),
('2024-04-17', '08:15:00', '16:45:00', 2, 2),
 ('2024-04-15', '08:00:00', '17:00:00', 3, 3);

 INSERT INTO `leave_request`(`reason`, `start_date`, `end_date`,`status`, `employee_id`, `department_id`)VALUES
 ('Vacation', '2024-05-01', '2024-05-07', 'PENDING', 1, 1),
 ('Family emergency', '2024-07-20', '2024-07-25', 'REJECTED', 2, 2),
 ('Medical leave', '2024-06-10', '2024-06-15', 'APPROVED', 1, 3);

 INSERT INTO `project`(`name`, `description`, `start_date`,`end_date`, `budget`, `status`)VALUES
 ('New Website Development', 'Develop a responsive website for the company', '2024-05-01', '2024-09-30', 5000, 'IN PROGRESS'),
 ('Product Launch Campaign', 'Plan and execute a marketing campaign for new product launch', '2024-06-15', '2024-08-31', 10000, 'PLANNED'),
 ('Office Renovation', 'Renovate the office space to improve working environment', '2024-07-01', '2024-10-31', 20000, 'IN PROGRESS');

 INSERT INTO `project_employee`(`employee_id`,`project_id`)VALUES
 ('1','1'),
 ('2','1');









