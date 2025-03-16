-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 16, 2025 at 03:31 PM
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
  `c_units` int(11) NOT NULL,
  `c_prerequisites` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `u_password` varchar(50) DEFAULT NULL,
  `u_role` varchar(50) DEFAULT NULL,
  `u_status` varchar(255) NOT NULL,
  `enrollment_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`u_id`, `u_fname`, `u_mname`, `u_lname`, `u_email`, `u_contact_number`, `u_username`, `u_password`, `u_role`, `u_status`, `enrollment_status`) VALUES
(1, 'adminaxcee', 'adminaxcee', 'adminaxcee', 'adminaxcee', 'adminaxcee@gmail.com', 'adminaxcee', 'adminaxcee', 'Admin', '', ''),
(2, 'Axcee', 'Axcee', 'Axcee', 'Axcee@gmail.com', 'user', 'user', 'user', 'User', '', 'Not Enrolled'),
(3, 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'User', '', 'Enrolled'),
(5, 'Axcee', 'Felisilda', 'Cabusas', 'axceelfelis03@gmail.com', '9914082061', 'axcee1', 'axcee123', 'User', 'Active', 'Not Enrolled'),
(6, 'test34', 'test34', 'test34', 'test34', 'test34', 'test34', 'test34', 'User', '', ''),
(9, 'test', 'test', 'test', 'test@gmail.com', '9459804057', 'testtest', 'testtest', 'User', 'Inactive', ''),
(10, 'qweqwe', 'qweqwe', 'qweqweq', 'weqeqw@gmail.com', '9914082061', 'qweqw', 'qweqweqwe', 'User', 'Inactive', ''),
(11, 'qweqweqw', 'eqweqw', 'eqweqeq', 'eqwewq@gmail.com', '9914082061', 'eqweqwe', 'qweqweqw', 'User', 'Inactive', ''),
(12, 'qweqw', 'qweqwe', 'qweqw', 'eqweq@gmail.com', '9459804057', 'qweqweq', 'qweqw2323', 'User', 'Inactive', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`c_id`);

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
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

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
