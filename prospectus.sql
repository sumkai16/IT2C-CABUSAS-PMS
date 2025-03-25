-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2025 at 04:17 PM
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
  `c_code` int(11) NOT NULL,
  `c_desc` int(11) NOT NULL,
  `c_units` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:12:50'),
(2, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:20:18'),
(3, 20251017, 'User Login', 'User logged in: rashed', '2025-03-25 14:22:17'),
(4, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:22:30'),
(5, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:24:33'),
(6, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:28:34'),
(7, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:30:28'),
(8, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:32:38'),
(9, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:34:15'),
(10, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:35:25'),
(11, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:38:27'),
(12, 20251017, 'User Login', 'User logged in: rashed', '2025-03-25 14:43:33'),
(13, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:43:44'),
(14, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:48:32'),
(15, 20251001, 'User Logout', 'User logged out: axceead', '2025-03-25 14:48:38'),
(16, 20251017, 'User Login', 'User logged in: rashed', '2025-03-25 14:48:45'),
(17, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:48:51'),
(18, 20251017, 'User Login', 'User logged in: rashed', '2025-03-25 14:49:54'),
(19, 20251017, 'User Login', 'User logged in: rashed', '2025-03-25 14:50:39'),
(20, 20251017, 'User Logout', 'User logged out: rashed', '2025-03-25 14:50:41'),
(21, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:50:46'),
(22, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:53:49'),
(23, 20251001, 'User Login', 'User logged in: axceead', '2025-03-25 14:58:17'),
(24, 20251001, 'Updated User', 'User Successfully updated: axceead', '2025-03-25 14:58:34'),
(25, 20251017, 'Login', 'User logged in: rashed', '2025-03-25 15:02:15'),
(26, 20251017, 'Logout', 'User logged out: rashed', '2025-03-25 15:02:17'),
(27, 20251001, 'Login', 'User logged in: axceead', '2025-03-25 15:02:20'),
(28, 20251001, 'Login', 'User logged in: axceead', '2025-03-25 15:02:59'),
(29, 20251001, 'Updated User', 'User Successfully updated: inso', '2025-03-25 15:03:08'),
(30, 20251001, 'Login', 'User logged in: axceead', '2025-03-25 15:03:34'),
(31, 20251001, 'Login', 'User logged in: axceead', '2025-03-25 15:04:05'),
(32, 20251001, 'Login', 'User logged in: axceead', '2025-03-25 15:04:47');

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

-- --------------------------------------------------------

--
-- Table structure for table `prospectus`
--

CREATE TABLE `prospectus` (
  `pr_id` int(11) NOT NULL,
  `program_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `pr_effective_year` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `s_id` int(11) NOT NULL,
  `s_fname` varchar(255) NOT NULL,
  `s_mname` varchar(255) NOT NULL,
  `s_lname` varchar(255) NOT NULL,
  `s_bdate` date NOT NULL,
  `s_address` text NOT NULL,
  `s_year` int(11) NOT NULL,
  `s_program` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `recovery_phrase` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`u_id`, `u_fname`, `u_mname`, `u_lname`, `u_email`, `u_contact_number`, `u_username`, `u_password`, `u_role`, `u_status`, `enrollment_status`, `recovery_phrase`) VALUES
(20251001, 'Axcee', 'Felisilda', 'Cabusas', 'axceelfelis03@gmail.com', '99140820611', 'axceead', 'lfSpezuM6snazqzIynAFQyu2izwBASU822ehKzaLWqg=', 'Admin', 'Active', 'Enrolled', ''),
(20251017, 'rashed', 'wahaha', 'rashed', 'rashed@gmail.com', '98273645671', 'rashed', 'lfSpezuM6snazqzIynAFQyu2izwBASU822ehKzaLWqg=', 'User', 'Active', 'Not Enrolled', 'jungle sunflower waterfall grape volcano sunflower umbrella tiger apple banana grape mountain'),
(20251018, 'addrianne', 'brobro', 'inso', 'inso12@gmail.com', '99872635678', 'inso', 'ZMI04LI4wpHFQpAFMuXoV2yxsvqk4QMRbYUIAql7DWI=', 'User', 'Active', 'Not Enrolled', 'elephant waterfall jungle kangaroo banana jungle dog flower waterfall grape waterfall queen');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`c_id`);

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
  ADD KEY `course_id` (`course_id`) USING BTREE;

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`s_id`);

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
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `program`
--
ALTER TABLE `program`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `prospectus`
--
ALTER TABLE `prospectus`
  MODIFY `pr_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20251019;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `prospectus`
--
ALTER TABLE `prospectus`
  ADD CONSTRAINT `prospectus_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`c_id`),
  ADD CONSTRAINT `prospectus_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `program` (`p_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
