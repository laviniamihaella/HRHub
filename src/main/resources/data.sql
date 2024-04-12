-- Inserare în tabela `department`
INSERT INTO `department` (`name`, `description`) VALUES
('Hr', 'Descriere pentru departamentul HR'),
('It', 'Descriere pentru departamentul IT'),
('Logistic', 'Descriere pentru departamentul Logistic');

-- Inserare în tabela `job_position`
INSERT INTO `job_position` (`name`, `description`, `requests`, `responsibilities`, `department_id`) VALUES
('Junior Hr', 'Descriere pentru poziția de junior HR', 'a', 'Responsabilitățile pentru poziția de junior HR', 1),
('Hr junior', 'Descriere pentru poziția de HR junior', 'a', 'Responsabilitățile pentru poziția de HR junior', 2),
('It', 'Descriere pentru poziția de IT', 'a', 'Responsabilitățile pentru poziția de IT', 3);

-- Inserare în tabela `employee`
INSERT INTO `employee` (`first_Name`, `last_Name`, `birth_Date`, `address`, `salary`, `job_position_id`, `department_id`) VALUES
('John', 'Doe', '1990-05-15', '123 Main Street', 22, 1, 1),
('Alice', 'Smith', '1985-08-20', '456 Elm Street', 24, 2, 2),
('Bob', 'Johnson', '1978-03-10', '789 Oak Avenue', 222, 3, 3);




