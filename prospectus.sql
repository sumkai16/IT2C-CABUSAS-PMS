-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 26, 2025 at 02:46 AM
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
(70, 'OJT', 'Internship / OJT / Practicum', 9, NULL),
(71, 'PATHFIT3', 'Individual and Dual Sports', 2, 23);

-- --------------------------------------------------------

--
-- Table structure for table `enrollment`
--

CREATE TABLE `enrollment` (
  `enrollment_id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `prog_id` int(11) NOT NULL,
  `enrollment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `semester` text NOT NULL,
  `enrollment_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollment`
--

INSERT INTO `enrollment` (`enrollment_id`, `userID`, `prog_id`, `enrollment_date`, `semester`, `enrollment_status`) VALUES
(1, 20251017, 1, '2025-04-24 12:41:59', '1st', ''),
(2, 20251020, 1, '2025-04-27 15:17:17', '1st', ''),
(3, 20251019, 1, '2025-05-15 02:13:19', '1st', 'Regular'),
(4, 20251019, 1, '2025-05-15 13:04:55', '1st', 'Regular');

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
(623, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-25 11:43:51'),
(624, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:44:15'),
(625, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:45:49'),
(626, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:46:35'),
(627, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:55:33'),
(628, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:55:56'),
(629, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:56:19'),
(630, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:56:37'),
(631, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:56:52'),
(632, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:58:47'),
(633, 20251020, 'Login', 'User logged in: mica', '2025-04-25 13:59:45'),
(634, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 14:06:48'),
(635, 20251001, 'Logout', 'Admin logged out: axcee', '2025-04-25 14:06:53'),
(636, 20251020, 'Login', 'User logged in: mica', '2025-04-25 14:06:59'),
(637, 20251020, 'Login', 'User logged in: mica', '2025-04-25 14:07:53'),
(638, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 14:12:11'),
(639, 20251017, 'Login', 'User logged in: rashed', '2025-04-25 14:13:00'),
(640, 20251001, 'Login', 'User logged in: axcee', '2025-04-25 14:16:15'),
(641, 20251001, 'Login', 'User logged in: axcee', '2025-04-26 11:11:00'),
(642, 20251001, 'User', 'User updated Successfully!: axcee', '2025-04-26 11:12:59'),
(643, 20251001, 'Logout', 'Admin logged out: axcee', '2025-04-26 11:13:14'),
(644, 20251021, 'Login', 'User logged in: ale', '2025-04-26 11:13:19'),
(645, 20251021, 'Logout', 'User logged out: ale', '2025-04-26 11:13:37'),
(646, 20251001, 'Login', 'User logged in: axcee', '2025-04-26 16:40:41'),
(647, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-26 16:40:57'),
(648, 20251001, 'Login', 'User logged in: axcee', '2025-04-26 17:12:39'),
(649, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-26 17:13:07'),
(650, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-26 17:17:07'),
(651, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-26 17:17:11'),
(652, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 00:53:54'),
(653, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 00:55:06'),
(654, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 00:56:07'),
(655, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 00:56:11'),
(656, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 00:56:18'),
(657, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 00:58:41'),
(658, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:00:13'),
(659, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:03:41'),
(660, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:07:51'),
(661, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:09:11'),
(662, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-27 01:09:37'),
(663, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:10:25'),
(664, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:10:49'),
(665, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:11:32'),
(666, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:12:19'),
(667, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-27 01:12:45'),
(668, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:12:55'),
(669, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:17:31'),
(670, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:17:39'),
(671, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:17:44'),
(672, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:18:21'),
(673, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-27 01:18:58'),
(674, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:19:01'),
(675, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 01:22:36'),
(676, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:22:38'),
(677, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-04-27 01:23:01'),
(678, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 01:23:04'),
(679, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 06:33:02'),
(680, 20251017, 'Prospectus', 'Viewed Prospectus.: rashed', '2025-04-27 06:33:04'),
(681, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 13:24:23'),
(682, 20251017, 'Prospectus', 'Viewed Prospectus.: rashed', '2025-04-27 13:24:26'),
(683, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 13:30:21'),
(684, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 13:30:52'),
(685, 20251017, 'Prospectus', 'Viewed Prospectus.: rashed', '2025-04-27 13:31:59'),
(686, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 14:00:10'),
(687, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 14:02:34'),
(688, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 14:06:22'),
(689, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 14:07:40'),
(690, 20251017, 'Prospectus', 'Viewed Prospectus.: rashed', '2025-04-27 14:08:59'),
(691, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 14:13:28'),
(692, 20251017, 'Login', 'User logged in: rashed', '2025-04-27 15:13:53'),
(693, 20251017, 'Prospectus', 'Viewed Prospectus.: rashed', '2025-04-27 15:13:55'),
(694, 20251001, 'Login', 'User logged in: axcee', '2025-04-27 15:14:40'),
(695, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-04-27 15:14:43'),
(696, 20251020, 'Login', 'User logged in: mica', '2025-04-27 15:16:10'),
(697, 20251020, 'Logout', 'User logged out: mica', '2025-04-27 15:17:20'),
(698, 20251020, 'Login', 'User logged in: mica', '2025-04-27 15:17:24'),
(699, 20251020, 'Prospectus', 'Viewed Prospectus.: mica', '2025-04-27 15:17:28'),
(700, 20251019, 'Login', 'User logged in: aljon', '2025-05-15 02:12:29'),
(701, 20251019, 'Logout', 'User logged out: aljon', '2025-05-15 02:13:26'),
(702, 20251019, 'Login', 'User logged in: aljon', '2025-05-15 02:13:31'),
(703, 20251019, 'Logout', 'User logged out: aljon', '2025-05-15 02:13:55'),
(704, 20251001, 'Login', 'User logged in: axcee', '2025-05-15 02:14:20'),
(705, 20251001, 'Logout', 'Admin logged out: axcee', '2025-05-15 02:14:43'),
(706, 20251019, 'Login', 'User logged in: aljon', '2025-05-15 13:04:33'),
(707, 20251019, 'Logout', 'User logged out: aljon', '2025-05-15 13:04:59'),
(708, 20251019, 'Login', 'User logged in: aljon', '2025-05-15 13:05:04'),
(709, 20251019, 'Login', 'User logged in: aljon', '2025-05-15 13:07:22'),
(710, 20251001, 'Login', 'User logged in: axcee', '2025-05-15 13:14:09'),
(711, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-15 13:14:12'),
(712, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-05-15 13:15:09'),
(713, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-15 13:15:16'),
(714, 20251001, 'Login', 'User logged in: axcee', '2025-05-15 13:23:04'),
(715, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-15 13:23:08'),
(716, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-05-15 13:23:33'),
(717, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-15 13:23:36'),
(718, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-05-15 13:25:41'),
(719, 20251001, 'Prospectus', 'Prospectus added successfully!: axcee', '2025-05-15 13:26:11'),
(720, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-15 13:26:15'),
(721, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-15 13:26:21'),
(722, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 11:56:13'),
(723, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 12:17:00'),
(724, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 12:19:44'),
(725, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 12:21:21'),
(726, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 12:21:24'),
(727, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 12:21:44'),
(728, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 12:21:50'),
(729, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 12:26:14'),
(730, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 12:26:23'),
(731, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 13:51:51'),
(732, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 13:51:55'),
(733, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 13:54:28'),
(734, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 13:54:34'),
(735, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 13:55:19'),
(736, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 13:57:23'),
(737, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 13:57:26'),
(738, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:01:21'),
(739, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:01:25'),
(740, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:04:23'),
(741, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:04:26'),
(742, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:08:52'),
(743, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:08:54'),
(744, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:09:15'),
(745, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:12:46'),
(746, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:12:49'),
(747, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:15:24'),
(748, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:15:27'),
(749, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:15:50'),
(750, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:17:58'),
(751, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:18:00'),
(752, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:18:57'),
(753, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:19:00'),
(754, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 14:23:42'),
(755, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 14:23:44'),
(756, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 15:47:10'),
(757, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 15:47:12'),
(758, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 15:49:05'),
(759, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 15:49:07'),
(760, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 15:57:48'),
(761, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 15:58:57'),
(762, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:00:01'),
(763, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 16:06:18'),
(764, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:06:22'),
(765, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:07:14'),
(766, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:07:42'),
(767, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:07:53'),
(768, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:10:12'),
(769, 20251001, 'Course', 'Course Added Successfully: axcee', '2025-05-16 16:10:51'),
(770, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:11:12'),
(771, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:11:20'),
(772, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:12:04'),
(773, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:13:23'),
(774, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:13:56'),
(775, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:14:00'),
(776, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:14:09'),
(777, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:14:27'),
(778, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:14:33'),
(779, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 16:14:54'),
(780, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:14:58'),
(781, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:15:28'),
(782, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:15:30'),
(783, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:16:09'),
(784, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:16:56'),
(785, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:17:00'),
(786, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 16:22:41'),
(787, 20251001, 'Prospectus', 'Prospectus added/updated successfully!: axcee', '2025-05-16 16:22:58'),
(788, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:23:06'),
(789, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:23:19'),
(790, 20251001, 'Login', 'User logged in: axcee', '2025-05-16 16:25:07'),
(791, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:25:09'),
(792, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:25:16'),
(793, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:25:24'),
(794, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-16 16:25:32'),
(795, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 14:53:35'),
(796, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:02:32'),
(797, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:02:59'),
(798, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:03:21'),
(799, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:07:15'),
(800, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:09:38'),
(801, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-23 15:09:42'),
(802, 20251001, 'Prospectus', 'Prospectus updated successfully: axcee', '2025-05-23 15:10:07'),
(803, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-23 15:10:12'),
(804, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:12:59'),
(805, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:16:17'),
(806, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-23 15:16:20'),
(807, 20251001, 'Login', 'User logged in: axcee', '2025-05-23 15:16:45'),
(808, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-23 15:16:48'),
(809, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:00:48'),
(810, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:23:03'),
(811, 20251019, 'Login', 'User logged in: aljon', '2025-05-25 13:26:18'),
(812, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:26:31'),
(813, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:38:04'),
(814, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:53:10'),
(815, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:54:11'),
(816, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 13:57:04'),
(817, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-25 13:57:40'),
(818, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:03:23'),
(819, 20251001, 'Prospectus', 'Prospectus updated successfully: axcee', '2025-05-25 14:05:17'),
(820, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-25 14:05:21'),
(821, 20251001, 'Prospectus', 'Prospectus updated successfully: axcee', '2025-05-25 14:08:00'),
(822, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:09:35'),
(823, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:12:24'),
(824, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:13:29'),
(825, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:14:47'),
(826, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:16:43'),
(827, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:27:03'),
(828, 20251001, 'Prospectus', 'Viewed Prospectus.: axcee', '2025-05-25 14:36:13'),
(829, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 14:57:35'),
(830, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 15:04:24'),
(831, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 15:10:12'),
(832, 20251001, 'Login', 'User logged in: axcee', '2025-05-25 15:12:45'),
(833, 20251001, 'Prospectus', 'Prospectus updated successfully: axcee', '2025-05-25 15:13:00');

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
(25, 1, '2023-2024', 'Active', 20251001, '2025-05-16 16:07:14', '2025-05-16 16:07:14'),
(26, 2, '2023-2024', 'Active', 20251001, '2025-05-16 16:16:56', '2025-05-16 16:16:56'),
(27, 1, '2024-2025', 'Active', 20251001, '2025-05-16 16:22:58', '2025-05-16 16:22:58');

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
(202, 25, 15, '1st Year', '2nd Semester'),
(203, 25, 16, '1st Year', '2nd Semester'),
(204, 25, 17, '1st Year', '2nd Semester'),
(205, 25, 18, '1st Year', '2nd Semester'),
(206, 25, 19, '1st Year', '2nd Semester'),
(207, 25, 20, '1st Year', '2nd Semester'),
(208, 25, 21, '1st Year', '2nd Semester'),
(209, 25, 22, '1st Year', '2nd Semester'),
(210, 25, 23, '1st Year', '2nd Semester'),
(304, 25, 36, '2nd Year', '1st Semester'),
(305, 25, 33, '2nd Year', '1st Semester'),
(306, 25, 34, '2nd Year', '1st Semester'),
(307, 25, 24, '2nd Year', '1st Semester'),
(308, 25, 26, '2nd Year', '1st Semester'),
(309, 25, 35, '2nd Year', '1st Semester'),
(310, 25, 71, '2nd Year', '1st Semester'),
(311, 25, 25, '2nd Year', '1st Semester'),
(312, 25, 37, '2nd Year', '1st Semester'),
(313, 25, 27, '2nd Year', '1st Semester'),
(314, 25, 9, '1st Year', '1st Semester'),
(315, 25, 11, '1st Year', '1st Semester'),
(316, 25, 7, '1st Year', '1st Semester'),
(317, 25, 3, '1st Year', '1st Semester'),
(318, 25, 4, '1st Year', '1st Semester'),
(319, 25, 6, '1st Year', '1st Semester'),
(320, 25, 5, '1st Year', '1st Semester'),
(321, 25, 12, '1st Year', '1st Semester'),
(322, 25, 14, '1st Year', '1st Semester'),
(323, 25, 13, '1st Year', '1st Semester'),
(324, 25, 10, '1st Year', '1st Semester'),
(325, 25, 8, '1st Year', '1st Semester');

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
(8, 20251017, 'Rashed', '', 'Tapales', '2004-06-28', 'San Fernando', 'Male', 1, 1, 'SCC', 'src/prospectus/images/student/default-user.png'),
(9, 20251020, 'Myca', '', 'Lariosa', '1560-04-08', 'Vito, Minglanilla', 'Female', 3, 1, 'SCC', 'src/prospectus/images/student/default-user.png'),
(10, 20251019, 'Aljon', '', 'Paragoso', '2000-01-02', 'Inayagan', 'Male', 3, 1, 'SCC', 'src/prospectus/images/student/default-user.png');

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
(20251019, 'Aljon', '', 'Paragoso', 'aljon@gmail.com', '9872634567', 'aljon', '4Sbb5S0/5dD/PgMPKbxnvhdqH4oUlAoorkqbhnsOiVY=', 'Student', 'Active', 'enrolled', 'jungle banana volcano tiger zebra grape house queen xylophone flower xylophone waterfall', 'src/prospectus/images/users/Screenshot_1.png'),
(20251020, 'Mica', '', 'Lariosa', 'mica@gmail.com', '9876153789', 'mica', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'Student', 'Active', 'Enrolled', 'zebra jungle xylophone queen kangaroo umbrella yellow mountain penguin tiger cherry apple', 'src/prospectus/images/users/default-user.png'),
(20251021, 'Alessandra Nayeli', '', 'Crispin', 'alenaye@gmail.com', '9827364512', 'ale', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'User ', 'Active', 'Not Enrolled', 'zebra orange elephant house house waterfall volcano jungle rainbow sunflower notebook rainbow', 'src/prospectus/images/users/default-user.png');

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
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT for table `enrollment`
--
ALTER TABLE `enrollment`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=834;

--
-- AUTO_INCREMENT for table `program`
--
ALTER TABLE `program`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `prospectus`
--
ALTER TABLE `prospectus`
  MODIFY `pr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `prospectus_details`
--
ALTER TABLE `prospectus_details`
  MODIFY `pd_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=326;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20251022;

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
  ADD CONSTRAINT `prospectus_details_ibfk_1` FOREIGN KEY (`pr_id`) REFERENCES `prospectus` (`pr_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `prospectus_details_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE;

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
