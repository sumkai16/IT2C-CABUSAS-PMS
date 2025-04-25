-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2025 at 01:47 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prospectus`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `c_id` int(11) NOT NULL,
  `c_code` varchar(255) NOT NULL,
  `c_desc` varchar(255) NOT NULL,
  `c_units` int(11) NOT NULL,
  `prerequisite_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`c_id`, `c_code`, `c_desc`, `c_units`, `prerequisite_id`) VALUES
(3, 'GE1', 'Understanding the Self', 3, NULL),
(4, 'GE2', 'Ethics', 3, NULL),
(5, 'GE6', 'Mathematics in the Modern World', 3, NULL),
(6, 'GE4', 'Science, Technology, and Society', 3, NULL),
(7, 'FIL1', 'Wikang Filipino', 3, NULL),
(8, 'CC101', 'Introduction to Computing', 3, NULL),
(9, 'CC102', 'Computer Programming', 3, NULL),
(10, 'REED1', 'Salvation History', 3, NULL),
(11, 'ENGPLUS', 'English Enhancement', 3, NULL),
(12, 'MATHPLUS', 'Basic Mathematics', 3, NULL),
(13, 'PATHFIT1', 'Movement Competency Training', 2, NULL),
(14, 'NSTP1', 'National Services Training Program', 3, NULL),
(15, 'FL', 'Foreign Language', 3, NULL),
(16, 'FIL2', 'Masining na Pagpapahayag', 3, 7),
(17, 'GE5', 'Purposive Communication', 3, NULL),
(18, 'GE7', 'Contemporay World', 3, NULL),
(19, 'HCI101', 'Introduction to Human Computer', 3, 8),
(20, 'CC103', 'Computer Programming 2', 3, 9),
(21, 'MS101', 'Discrete Mathematics', 3, NULL),
(22, 'NSTP2', 'National Services Training Program 2', 3, 14),
(23, 'PATHFIT2', 'Exercise Based Fitness', 2, 13),
(24, 'GE8', 'Art Appreciation', 3, NULL),
(25, 'PF201', 'Object Oriented Programming 1', 3, NULL),
(26, 'HUM1', 'Logic', 3, NULL),
(27, 'SOCI', 'Economics, Taxation and Land Reform', 3, NULL),
(33, 'CC203', 'Information Management 1', 3, 20),
(34, 'CC204', 'Data Structures and Algorithm', 3, 21),
(35, 'IT201', 'PC Assembling and Disassembling', 3, 8),
(36, 'ACCTG', 'Principles of Accounting', 3, NULL),
(37, 'REED2', 'Christology', 3, 10),
(38, 'GE3', 'Reading in Philippine History', 3, NULL),
(39, 'PF205', 'Object Oriented Programming 2', 3, 25),
(40, 'PGC', 'Phil. Governance and Constitution', 3, NULL),
(41, 'IM207', 'Fundamentals of Database System', 3, 33),
(42, 'NET208', 'Networking', 3, NULL),
(43, 'IPT209', 'Integrative Programming and Technology', 3, 19),
(44, 'MATH3', 'Probability & Statistics', 3, NULL),
(45, 'REED3', 'Sacraments, Church and Christian Morality', 3, 37),
(46, 'PATHFIT4', 'Team Sports/Games', 2, 23),
(47, 'WS301', 'Elective 1', 3, 43),
(48, 'NATSCI1', 'Physical Science', 3, NULL),
(49, 'SIA304', 'Systems Integration and Architecture', 3, 35),
(50, 'HUM2', 'Intro to Literature', 3, NULL),
(51, 'PT206', 'Project Management', 3, 33),
(52, 'IT306', 'Multimedia Systems', 3, 51),
(53, 'IT307', 'System Analysis and Design', 3, 41),
(54, 'PT300', 'Free Elective', 3, 19),
(55, 'REED4', 'Christian Commitment & Responsibility', 3, 45),
(56, 'GE9', 'Life and Works of Rizal & Other Heroes', 3, NULL),
(57, 'IT308', 'Software Engineering', 3, 34),
(58, 'MS309', 'Quantitative Methods', 3, 21),
(59, 'WS310', 'Elective 2', 3, 47),
(60, 'IAS311', 'Information Assurance and Security', 3, 8),
(61, 'SIA312', 'Free Elective', 3, 49),
(62, 'NATSCI2', 'College Physics', 3, 48),
(63, 'CAP314', 'Capstone Project 1', 3, NULL),
(64, 'CC313', 'Application Development and Emerging Technologies', 3, 39),
(65, 'CAP401', 'Capstone Project 2', 3, 63),
(66, 'IT402', 'Free Elective', 3, 39),
(67, 'IT404', 'Seminars in IT Trends / Updates - Elective', 3, NULL),
(68, 'SA405', 'System Administration and Maintenance', 3, NULL),
(69, 'SP403', 'Social and Professional Issues', 3, NULL),
(70, 'OJT', 'Internship / OJT / Practicum', 9, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `enrollment`
--

CREATE TABLE `enrollment` (
  `enrollment_id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `prog_id` int(11) NOT NULL,
  `enrollment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `semester` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollment`
--

INSERT INTO `enrollment` (`enrollment_id`, `userID`, `prog_id`, `enrollment_date`, `semester`) VALUES
(1, 20251017, 1, '2025-04-24 12:41:59', '1st');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` text NOT NULL,
  `description` text NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`log_id`, `user_id`, `action`, `description`, `date_time`) VALUES
(605, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 06:25:36'),
(606, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 06:27:01'),
(607, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 06:31:57'),
(608, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 06:33:42'),
(609, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 06:39:53'),
(610, 20251017, 'Login', 'User logged in: rashed', '2025-04-25 06:55:18'),
(611, 20251017, 'Prospectus', 'Viewed Prospectus.: rashed', '2025-04-25 06:55:21'),
(612, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 06:55:29'),
(613, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 11:10:00'),
(614, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-25 11:10:03'),
(615, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 11:12:18'),
(616, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-25 11:12:22'),
(617, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 11:15:25'),
(618, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-25 11:15:30'),
(619, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 11:36:03'),
(620, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-25 11:36:50'),
(621, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 11:43:23'),
(622, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-25 11:43:47'),
(623, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-25 11:43:51');

-- --------------------------------------------------------

--
-- Table structure for table `program`
--

CREATE TABLE `program` (
  `p_id` int(11) NOT NULL,
  `p_program_name` varchar(255) NOT NULL,
  `p_desc` varchar(255) NOT NULL,
  `p_department` varchar(255) NOT NULL,
  `p_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `program`
--

INSERT INTO `program` (`p_id`, `p_program_name`, `p_desc`, `p_department`, `p_status`) VALUES
(1, 'Bachelor of Science in Information Technology', 'College of Information Technology', 'BSIT', 'Active'),
(2, 'Bachelor of Science in Hospitality Management', 'College of Hospitality Management', 'BSHM', 'Active'),
(3, 'Bachelor of Science in Education', 'College of Education', 'BSED', 'Active'),
(4, 'Bachelor of Science in Criminology', ' College of Criminology', 'BSCRIM', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `prospectus`
--

CREATE TABLE `prospectus` (
  `pr_id` int(11) NOT NULL,
  `program_id` int(11) NOT NULL,
  `pr_effective_year` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prospectus`
--

INSERT INTO `prospectus` (`pr_id`, `program_id`, `pr_effective_year`, `status`, `created_by`, `created_at`, `updated_at`) VALUES
(2, 1, '2023-2024', 'Active', 20251001, '2025-04-22 12:18:53', '2025-04-22 12:18:53'),
(3, 1, '2023-2024', 'Active', 20251001, '2025-04-24 14:01:37', '2025-04-24 14:01:37'),
(4, 1, '2023-2024', 'Active', 20251001, '2025-04-24 14:03:56', '2025-04-24 14:03:56'),
(5, 1, '2023-2024', 'Active', 20251001, '2025-04-24 14:04:27', '2025-04-24 14:04:27'),
(6, 1, '2023-2024', 'Active', 20251001, '2025-04-24 14:05:11', '2025-04-24 14:05:11'),
(7, 1, '2023-2024', 'Active', 20251001, '2025-04-24 14:07:05', '2025-04-24 14:07:05'),
(9, 1, '2023-2024', 'Active', 20251001, '2025-04-25 05:32:53', '2025-04-25 05:32:53'),
(10, 1, '2023-2024', 'Active', 20251001, '2025-04-25 05:33:10', '2025-04-25 05:33:10'),
(11, 1, '2023-2024', 'Active', 20251001, '2025-04-25 11:36:50', '2025-04-25 11:36:50');

-- --------------------------------------------------------

--
-- Table structure for table `prospectus_details`
--

CREATE TABLE `prospectus_details` (
  `pd_id` int(11) NOT NULL,
  `pr_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `year_level` varchar(225) DEFAULT NULL,
  `semester` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prospectus_details`
--

INSERT INTO `prospectus_details` (`pd_id`, `pr_id`, `course_id`, `year_level`, `semester`) VALUES
(2, 2, 3, '1st Year', '1st Semester'),
(4, 2, 4, '1st Year', '1st Semester'),
(6, 2, 5, '1st Year', '1st Semester'),
(8, 2, 6, '1st Year', '1st Semester'),
(10, 2, 7, '1st Year', '1st Semester'),
(12, 2, 8, '1st Year', '1st Semester'),
(14, 2, 9, '1st Year', '1st Semester'),
(16, 2, 10, '1st Year', '1st Semester'),
(18, 2, 11, '1st Year', '1st Semester'),
(20, 2, 12, '1st Year', '1st Semester'),
(22, 2, 13, '1st Year', '1st Semester'),
(24, 2, 14, '1st Year', '1st Semester'),
(26, 3, 15, '1st Year', '2nd Semester'),
(28, 3, 16, '1st Year', '2nd Semester'),
(30, 3, 17, '1st Year', '2nd Semester'),
(32, 3, 18, '1st Year', '2nd Semester'),
(34, 3, 19, '1st Year', '2nd Semester'),
(36, 3, 20, '1st Year', '2nd Semester'),
(38, 3, 21, '1st Year', '2nd Semester'),
(40, 3, 22, '1st Year', '2nd Semester'),
(42, 3, 23, '1st Year', '2nd Semester'),
(44, 4, 24, '2nd Year', '1st Semester'),
(46, 4, 25, '2nd Year', '1st Semester'),
(48, 4, 26, '2nd Year', '1st Semester'),
(50, 4, 27, '2nd Year', '1st Semester'),
(52, 4, 33, '2nd Year', '1st Semester'),
(54, 4, 34, '2nd Year', '1st Semester'),
(56, 4, 35, '2nd Year', '1st Semester'),
(58, 4, 36, '2nd Year', '1st Semester'),
(60, 4, 37, '2nd Year', '1st Semester'),
(62, 5, 38, '2nd Year', '2nd Semester'),
(64, 5, 39, '2nd Year', '2nd Semester'),
(66, 5, 40, '2nd Year', '2nd Semester'),
(68, 5, 41, '2nd Year', '2nd Semester'),
(70, 5, 42, '2nd Year', '2nd Semester'),
(72, 5, 43, '2nd Year', '2nd Semester'),
(74, 5, 44, '2nd Year', '2nd Semester'),
(76, 5, 45, '2nd Year', '2nd Semester'),
(78, 5, 46, '2nd Year', '2nd Semester'),
(80, 6, 47, '3rd Year', '1st Semester'),
(82, 6, 48, '3rd Year', '1st Semester'),
(84, 6, 49, '3rd Year', '1st Semester'),
(86, 6, 50, '3rd Year', '1st Semester'),
(88, 6, 51, '3rd Year', '1st Semester'),
(90, 6, 52, '3rd Year', '1st Semester'),
(92, 6, 53, '3rd Year', '1st Semester'),
(94, 6, 54, '3rd Year', '1st Semester'),
(96, 6, 55, '3rd Year', '1st Semester'),
(98, 7, 56, '3rd Year', '2nd Semester'),
(100, 7, 57, '3rd Year', '2nd Semester'),
(102, 7, 58, '3rd Year', '2nd Semester'),
(104, 7, 59, '3rd Year', '2nd Semester'),
(106, 7, 60, '3rd Year', '2nd Semester'),
(108, 7, 61, '3rd Year', '2nd Semester'),
(110, 7, 62, '3rd Year', '2nd Semester'),
(117, 9, 65, '4th Year', '1st Semester'),
(119, 9, 66, '4th Year', '1st Semester'),
(121, 9, 67, '4th Year', '1st Semester'),
(123, 9, 69, '4th Year', '1st Semester'),
(125, 9, 68, '4th Year', '1st Semester'),
(127, 10, 70, '4th Year', '2nd Semester'),
(129, 11, 64, 'Summer', 'Summer'),
(131, 11, 63, 'Summer', 'Summer');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `s_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `s_fname` varchar(255) NOT NULL,
  `s_mname` varchar(255) NOT NULL,
  `s_lname` varchar(255) NOT NULL,
  `s_bdate` date NOT NULL,
  `s_address` text NOT NULL,
  `s_sex` varchar(255) NOT NULL,
  `s_year` int(11) NOT NULL,
  `s_program` int(11) DEFAULT NULL,
  `previous_school` varchar(255) NOT NULL,
  `s_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`s_id`, `u_id`, `s_fname`, `s_mname`, `s_lname`, `s_bdate`, `s_address`, `s_sex`, `s_year`, `s_program`, `previous_school`, `s_image`) VALUES
(8, 20251017, 'Rashed', '', 'Tapales', '2004-06-28', 'San Fernando', 'Male', 1, 1, 'SCC', 'src/prospectus/images/student/default-user.png');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `u_id` int(11) NOT NULL,
  `u_fname` varchar(50) DEFAULT NULL,
  `u_mname` varchar(50) DEFAULT NULL,
  `u_lname` varchar(50) DEFAULT NULL,
  `u_email` varchar(50) DEFAULT NULL,
  `u_contact_number` varchar(50) DEFAULT NULL,
  `u_username` varchar(50) DEFAULT NULL,
  `u_password` varchar(255) DEFAULT NULL,
  `u_role` varchar(50) DEFAULT NULL,
  `u_status` varchar(255) NOT NULL,
  `enrollment_status` varchar(255) NOT NULL,
  `recovery_phrase` varchar(255) NOT NULL,
  `u_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`u_id`, `u_fname`, `u_mname`, `u_lname`, `u_email`, `u_contact_number`, `u_username`, `u_password`, `u_role`, `u_status`, `enrollment_status`, `recovery_phrase`, `u_image`) VALUES
(20251001, 'Axcee', 'Felisilda', 'Cabusas', 'axceelfelis03@gmail.com', '99140820611', 'axcee', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'Admin', 'Active', 'Not Enrolled', '1234', 'src/prospectus/images/users/default-user.png'),
(20251017, 'Rasheed', '', 'Tapales', 'rashed@gmail.com', '98273645671', 'rashed', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'Student', 'Active', 'Enrolled', 'jungle sunflower waterfall grape volcano sunflower umbrella tiger apple banana grape mountain', 'src/prospectus/images/users/default-user.png'),
(20251018, 'Adrianne Lhoue', '', 'Inso', 'inso12@gmail.com', '99872635678', 'inso', 'ZMI04LI4wpHFQpAFMuXoV2yxsvqk4QMRbYUIAql7DWI=', 'User', 'Active', 'Not Enrolled', 'elephant waterfall jungle kangaroo banana jungle dog flower waterfall grape waterfall queen', 'src/prospectus/images/users/received_178815621514415.jpg'),
(20251019, 'Aljon', '', 'Paragoso', 'aljon@gmail.com', '9872634567', 'aljon', '4Sbb5S0/5dD/PgMPKbxnvhdqH4oUlAoorkqbhnsOiVY=', 'Student', 'Active', 'Not Enrolled', 'jungle banana volcano tiger zebra grape house queen xylophone flower xylophone waterfall', 'src/prospectus/images/users/Screenshot_1.png'),
(20251020, 'Mica', '', 'Lariosa', 'mica@gmail.com', '9876153789', 'mica', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'Student', 'Active', 'Not Enrolled', 'zebra jungle xylophone queen kangaroo umbrella yellow mountain penguin tiger cherry apple', 'src/prospectus/images/users/default-user.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`c_id`),
  ADD KEY `prerequisite_id` (`prerequisite_id`);

--
-- Indexes for table `enrollment`
--
ALTER TABLE `enrollment`
  ADD PRIMARY KEY (`enrollment_id`),
  ADD KEY `userID` (`userID`),
  ADD KEY `prog_id` (`prog_id`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `program`
--
ALTER TABLE `program`
  ADD PRIMARY KEY (`p_id`);

--
-- Indexes for table `prospectus`
--
ALTER TABLE `prospectus`
  ADD PRIMARY KEY (`pr_id`),
  ADD KEY `program_id` (`program_id`),
  ADD KEY `created_by` (`created_by`);

--
-- Indexes for table `prospectus_details`
--
ALTER TABLE `prospectus_details`
  ADD PRIMARY KEY (`pd_id`),
  ADD KEY `pr_id` (`pr_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`s_id`),
  ADD KEY `u_id` (`u_id`) USING BTREE,
  ADD KEY `s_program` (`s_program`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT for table `enrollment`
--
ALTER TABLE `enrollment`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=624;

--
-- AUTO_INCREMENT for table `program`
--
ALTER TABLE `program`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `prospectus`
--
ALTER TABLE `prospectus`
  MODIFY `pr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `prospectus_details`
--
ALTER TABLE `prospectus_details`
  MODIFY `pd_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20251021;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`prerequisite_id`) REFERENCES `course` (`c_id`);

--
-- Constraints for table `enrollment`
--
ALTER TABLE `enrollment`
  ADD CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`u_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`prog_id`) REFERENCES `program` (`p_id`) ON DELETE CASCADE;

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `prospectus`
--
ALTER TABLE `prospectus`
  ADD CONSTRAINT `prospectus_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `user` (`u_id`),
  ADD CONSTRAINT `prospectus_ibfk_3` FOREIGN KEY (`program_id`) REFERENCES `program` (`p_id`);

--
-- Constraints for table `prospectus_details`
--
ALTER TABLE `prospectus_details`
  ADD CONSTRAINT `prospectus_details_ibfk_1` FOREIGN KEY (`pr_id`) REFERENCES `prospectus` (`pr_id`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_program` FOREIGN KEY (`s_program`) REFERENCES `program` (`p_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_student_user` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
