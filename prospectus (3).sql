-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2025 at 05:36 PM
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
(14, 'NSTP1', 'National Services Training Program', 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `enrollment`
--

CREATE TABLE `enrollment` (
  `enrollment_id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `prog_id` int(11) NOT NULL,
  `enrollment_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollment`
--

INSERT INTO `enrollment` (`enrollment_id`, `userID`, `prog_id`, `enrollment_date`) VALUES
(6, 20251018, 2, '2025-04-17 12:44:11'),
(7, 20251001, 1, '2025-04-18 03:56:54'),
(14, 20251019, 1, '2025-04-18 04:33:51'),
(15, 20251001, 1, '2025-04-19 11:12:05'),
(16, 20251020, 3, '2025-04-20 12:32:40'),
(17, 20251020, 3, '2025-04-20 12:33:19');

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
(32, 20251001, 'Login', 'User logged in: axceead', '2025-03-25 15:04:47'),
(33, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 02:51:26'),
(34, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 02:58:18'),
(35, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 02:58:54'),
(36, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:01:06'),
(37, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:02:09'),
(38, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:03:30'),
(39, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:03:53'),
(40, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:06:03'),
(41, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:06:46'),
(42, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:07:42'),
(43, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:12:28'),
(44, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:13:45'),
(45, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:15:03'),
(46, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:19:13'),
(47, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:19:44'),
(48, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 03:20:17'),
(49, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 07:09:37'),
(50, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 07:12:55'),
(51, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 08:20:36'),
(52, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 08:50:02'),
(53, 20251001, 'Update', 'Updated user: inso. Changes: [Middle Name: brobro -> sam] ', '2025-03-27 08:50:29'),
(54, 20251017, 'Login', 'User logged in: rashed', '2025-03-27 11:52:21'),
(55, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 13:20:46'),
(56, 20251001, 'Login', 'User logged in: axceead', '2025-03-27 15:09:31'),
(57, 20251017, 'Login', 'User logged in: rashed', '2025-03-28 03:39:13'),
(58, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:02:14'),
(59, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:02:39'),
(60, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:03:44'),
(61, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:04:56'),
(62, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:07:01'),
(63, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:08:47'),
(64, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:09:48'),
(65, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:10:49'),
(66, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:27:59'),
(67, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:28:32'),
(68, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:31:14'),
(69, 20251001, 'Update', 'Updated user: rashed. Changes: [Role: User -> Admin] ', '2025-03-28 04:31:23'),
(70, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:33:20'),
(71, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:34:44'),
(72, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:44:52'),
(73, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:47:10'),
(74, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:53:27'),
(75, 20251001, 'User Added', 'Admin added a user axceead', '2025-03-28 04:54:26'),
(76, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 04:59:25'),
(77, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 05:01:38'),
(78, 20251001, 'Logout', 'Admin logged out: axceead', '2025-03-28 05:01:59'),
(79, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:20:00'),
(80, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:23:10'),
(81, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:23:54'),
(82, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:27:59'),
(83, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:29:49'),
(84, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:39:49'),
(85, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:40:42'),
(86, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:42:13'),
(87, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:42:56'),
(88, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:43:45'),
(89, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:44:48'),
(90, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:46:45'),
(91, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 10:47:12'),
(92, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 11:05:11'),
(93, 20251001, 'Program Added', 'Admin added a Program axceead', '2025-03-28 11:06:51'),
(94, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 11:09:38'),
(95, 20251001, 'Program Added', 'Admin added a Program axceead', '2025-03-28 11:10:53'),
(96, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 11:12:22'),
(97, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 11:13:57'),
(98, 20251001, 'Login', 'User logged in: axceead', '2025-03-28 14:35:06'),
(99, 20251001, 'Logout', 'Admin logged out: axceead', '2025-03-28 14:35:25'),
(100, 20251017, 'Login', 'User logged in: rashed', '2025-03-28 14:36:08'),
(101, 20251018, 'Login', 'User logged in: inso', '2025-03-28 14:36:22'),
(102, 20251018, 'Login', 'User logged in: inso', '2025-03-28 14:37:44'),
(103, 20251018, 'Login', 'User logged in: inso', '2025-03-28 14:39:01'),
(104, 20251017, 'Login', 'User logged in: rashed', '2025-03-29 01:47:00'),
(105, 20251017, 'Logout', 'Admin logged out: rashed', '2025-03-29 01:47:02'),
(106, 20251018, 'Login', 'User logged in: inso', '2025-03-29 01:47:06'),
(107, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 02:12:49'),
(108, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 02:13:43'),
(109, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 02:35:23'),
(110, 20251001, 'Update', 'Updated user: axceead. Changes: ', '2025-03-29 02:36:07'),
(111, 20251001, 'Update', 'Updated user: rashed. Changes: ', '2025-03-29 02:39:03'),
(112, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 02:43:53'),
(113, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 15:17:41'),
(114, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 15:35:02'),
(115, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 15:39:14'),
(116, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 15:40:57'),
(117, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 15:56:46'),
(118, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 15:57:52'),
(119, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:00:27'),
(120, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:03:41'),
(121, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:05:13'),
(122, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:05:54'),
(123, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:19:34'),
(124, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:20:17'),
(125, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:22:16'),
(126, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:32:04'),
(127, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:34:04'),
(128, 20251018, 'Logout', 'User logged out: inso', '2025-03-29 16:34:09'),
(129, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:34:13'),
(130, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:36:48'),
(131, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:39:18'),
(132, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:40:59'),
(133, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:43:07'),
(134, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:43:56'),
(135, 20251001, 'Login', 'User logged in: axceead', '2025-03-29 16:44:59'),
(136, 20251018, 'Login', 'User logged in: inso', '2025-03-29 16:50:10'),
(137, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 00:55:57'),
(138, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 00:58:27'),
(139, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:00:13'),
(140, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:00:38'),
(141, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:11:55'),
(142, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:13:07'),
(143, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:15:33'),
(144, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:16:44'),
(145, 20251001, 'User Added', 'Admin added a user aljon', '2025-03-30 01:17:10'),
(146, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:18:06'),
(147, 20251001, 'Logout', 'Admin logged out: axceead', '2025-03-30 01:18:56'),
(148, 20251019, 'Login', 'User logged in: aljon', '2025-03-30 01:19:00'),
(149, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:26:15'),
(150, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 01:27:22'),
(151, 20251019, 'Login', 'User logged in: aljon', '2025-03-30 10:49:36'),
(152, 20251019, 'Login', 'User logged in: aljon', '2025-03-30 10:52:16'),
(153, 20251019, 'Login', 'User logged in: aljon', '2025-03-30 10:56:24'),
(154, 20251019, 'Login', 'User logged in: aljon', '2025-03-30 10:57:33'),
(155, 20251018, 'Login', 'User logged in: inso', '2025-03-30 10:59:57'),
(156, 20251018, 'Logout', 'User logged out: inso', '2025-03-30 11:02:26'),
(157, 20251018, 'Login', 'User logged in: inso', '2025-03-30 11:02:29'),
(158, 20251018, 'Login', 'User logged in: inso', '2025-03-30 11:03:30'),
(159, 20251018, 'Login', 'User logged in: inso', '2025-03-30 11:04:38'),
(160, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 12:59:58'),
(161, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 13:03:27'),
(162, 20251001, 'Login', 'User logged in: axceead', '2025-03-30 13:03:46'),
(163, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 03:19:24'),
(164, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 04:29:13'),
(165, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 08:20:46'),
(166, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:36:54'),
(167, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:38:07'),
(168, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:38:42'),
(169, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:39:23'),
(170, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:42:13'),
(171, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:42:43'),
(172, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:43:00'),
(173, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:44:29'),
(174, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:46:05'),
(175, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:54:26'),
(176, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:56:12'),
(177, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 14:57:29'),
(178, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 15:03:32'),
(179, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 15:06:01'),
(180, 20251001, 'Login', 'User logged in: axceead', '2025-03-31 15:13:23'),
(181, 20251018, 'Login', 'User logged in: inso', '2025-04-01 13:04:18'),
(182, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 13:04:36'),
(183, 20251001, 'User Added', 'Admin added a user qwerty', '2025-04-01 13:05:19'),
(184, 20251001, 'Logout', 'Admin logged out: axceead', '2025-04-01 13:05:36'),
(185, 20251020, 'Login', 'User logged in: qwerty', '2025-04-01 13:05:42'),
(186, 20251020, 'Login', 'User logged in: qwerty', '2025-04-01 13:10:43'),
(187, 20251020, 'Login', 'User logged in: qwerty', '2025-04-01 13:11:59'),
(188, 20251020, 'Login', 'User logged in: qwerty', '2025-04-01 13:13:28'),
(189, 20251020, 'Login', 'User logged in: qwerty', '2025-04-01 13:14:24'),
(190, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 14:53:31'),
(191, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 14:54:06'),
(192, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 14:55:17'),
(193, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 15:05:53'),
(194, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 15:09:48'),
(195, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 15:13:14'),
(196, 20251001, 'Login', 'User logged in: axceead', '2025-04-01 15:13:43'),
(197, 20251001, 'Login', 'User logged in: axceead', '2025-04-02 14:34:01'),
(198, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 08:46:03'),
(199, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 08:46:28'),
(200, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 08:47:39'),
(201, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 08:50:57'),
(202, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 09:02:22'),
(203, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 09:03:41'),
(204, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 09:05:07'),
(205, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 09:11:45'),
(206, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 09:13:36'),
(207, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:33:12'),
(208, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:33:52'),
(209, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:37:17'),
(210, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:40:40'),
(211, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:48:49'),
(212, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:51:43'),
(213, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 10:54:14'),
(214, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:02:56'),
(215, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:04:14'),
(216, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:05:59'),
(217, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:07:27'),
(218, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:10:30'),
(219, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:12:08'),
(220, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:15:18'),
(221, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:19:33'),
(222, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:25:54'),
(223, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:26:35'),
(224, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 11:33:38'),
(225, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:03:05'),
(226, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:04:32'),
(227, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:06:44'),
(228, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:07:29'),
(229, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:08:07'),
(230, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:23:00'),
(231, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:33:46'),
(232, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:35:14'),
(233, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:35:43'),
(234, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:43:22'),
(235, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:45:13'),
(236, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:49:14'),
(237, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:57:54'),
(238, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 12:58:53'),
(239, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 13:02:24'),
(240, 20251001, 'Login', 'User logged in: axceead', '2025-04-03 15:37:52'),
(241, 20251001, 'Login', 'User logged in: axceead', '2025-04-05 12:44:32'),
(242, 20251001, 'Login', 'User logged in: axceead', '2025-04-05 13:00:10'),
(243, 20251001, 'Login', 'User logged in: axceead', '2025-04-05 13:00:36'),
(244, 20251001, 'Login', 'User logged in: axceead', '2025-04-05 13:02:20'),
(245, 20251001, 'Login', 'User logged in: axceead', '2025-04-05 23:46:22'),
(246, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:04:13'),
(247, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:07:15'),
(248, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:17:17'),
(249, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:19:09'),
(250, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:39:32'),
(251, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:41:34'),
(252, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:41:46'),
(253, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:46:03'),
(254, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:46:32'),
(255, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 00:47:01'),
(256, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:01:44'),
(257, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:08:39'),
(258, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:09:19'),
(259, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:20:40'),
(260, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:21:08'),
(261, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:26:11'),
(262, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:35:23'),
(263, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:36:48'),
(264, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:37:28'),
(265, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 01:38:48'),
(266, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 03:46:09'),
(267, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 14:20:30'),
(268, 20251001, 'Login', 'User logged in: axceead', '2025-04-06 14:21:47'),
(269, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:27:15'),
(270, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:32:38'),
(271, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:38:56'),
(272, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:40:23'),
(273, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:50:04'),
(274, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:54:42'),
(275, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:56:21'),
(276, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 03:59:18'),
(277, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 04:02:53'),
(278, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 04:03:22'),
(279, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 04:03:59'),
(280, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 04:05:59'),
(281, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 04:09:18'),
(282, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 07:29:31'),
(283, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 07:31:32'),
(284, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 07:31:55'),
(285, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 07:37:08'),
(286, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 07:38:30'),
(287, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 07:40:34'),
(288, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 11:39:07'),
(289, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 11:40:04'),
(290, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 11:44:19'),
(291, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 11:45:55'),
(292, 20251001, 'Logout', 'Admin logged out: axceead', '2025-04-17 11:46:40'),
(293, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:01:07'),
(294, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:08:18'),
(295, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:09:16'),
(296, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:13:08'),
(297, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:31:42'),
(298, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:41:56'),
(299, 20251018, 'Login', 'User logged in: inso', '2025-04-17 12:42:39'),
(300, 20251018, 'Login', 'User logged in: inso', '2025-04-17 12:43:46'),
(301, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:44:36'),
(302, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:46:42'),
(303, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:59:21'),
(304, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 12:59:50'),
(305, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 13:00:16'),
(306, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 13:00:31'),
(307, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:19:40'),
(308, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:27:35'),
(309, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:28:33'),
(310, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:29:08'),
(311, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:29:57'),
(312, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:30:09'),
(313, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:30:29'),
(314, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:32:35'),
(315, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:33:08'),
(316, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 02:37:38'),
(317, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 03:30:28'),
(318, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 03:35:34'),
(319, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 03:55:55'),
(320, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:02:13'),
(321, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:16:12'),
(322, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:22:54'),
(323, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:24:47'),
(324, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:28:22'),
(325, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:32:48'),
(326, 20251001, 'Login', 'User logged in: axceead', '2025-04-18 04:35:11'),
(327, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 03:21:57'),
(328, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 03:51:05'),
(329, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 03:55:07'),
(330, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:00:00'),
(331, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:02:33'),
(332, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:03:38'),
(333, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:29:28'),
(334, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:34:15'),
(335, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:36:12'),
(336, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:37:53'),
(337, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 04:38:17'),
(338, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 08:27:23'),
(339, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 08:28:33'),
(340, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 08:29:26'),
(341, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 09:48:04'),
(342, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 09:49:42'),
(343, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 09:53:06'),
(344, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 09:55:28'),
(345, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 09:56:21'),
(346, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 09:59:03'),
(347, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:02:46'),
(348, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:03:10'),
(349, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:09:52'),
(350, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:38:07'),
(351, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:45:43'),
(352, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:46:31'),
(353, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:47:48'),
(354, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:53:16'),
(355, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:57:10'),
(356, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 10:57:49'),
(357, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:09:34'),
(358, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:10:50'),
(359, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:11:13'),
(360, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:38:12'),
(361, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:40:44'),
(362, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:42:50'),
(363, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:55:05'),
(364, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:57:33'),
(365, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:58:09'),
(366, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 11:58:38'),
(367, 20251001, 'Login', 'User logged in: axceead', '2025-04-19 12:01:48'),
(368, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 12:27:14'),
(369, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 12:28:27'),
(370, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 12:41:51'),
(371, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 13:10:43'),
(372, 20251001, 'Program Added', 'Admin added a program Bachelor of Science in Criminology', '2025-04-20 13:11:33'),
(373, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 13:13:39'),
(374, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 13:18:27'),
(375, 20251001, 'Login', 'User logged in: axceead', '2025-04-20 13:20:01'),
(376, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 13:20:45'),
(377, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 13:21:50'),
(378, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:19:26'),
(379, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:21:16'),
(380, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:21:40'),
(381, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:24:07'),
(382, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:26:38'),
(383, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:30:09'),
(384, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:33:41'),
(385, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:37:06'),
(386, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:38:13'),
(387, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:39:13'),
(388, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:40:12'),
(389, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:41:17'),
(390, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:41:39'),
(391, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:43:33'),
(392, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:44:03'),
(393, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:45:42'),
(394, 20251001, 'Login', 'User logged in: axcee', '2025-04-20 14:49:09'),
(395, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 12:40:57'),
(396, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 12:54:07'),
(397, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 12:56:08'),
(398, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 12:57:09'),
(399, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:08:42'),
(400, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:10:45'),
(401, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:12:21'),
(402, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:13:24'),
(403, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:14:56'),
(404, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:19:48'),
(405, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:28:06'),
(406, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:29:47'),
(407, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:31:21'),
(408, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:33:31'),
(409, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:34:29'),
(410, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:36:52'),
(411, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:39:14'),
(412, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:40:20'),
(413, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:43:14'),
(414, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:44:45'),
(415, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:48:00'),
(416, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:54:24'),
(417, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:55:33'),
(418, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:56:40'),
(419, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 13:57:55'),
(420, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 14:28:46'),
(421, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 14:31:22'),
(422, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:04:20'),
(423, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:07:47'),
(424, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:12:29'),
(425, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:20:15'),
(426, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:21:37'),
(427, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:22:40'),
(428, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:23:30'),
(429, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:24:01'),
(430, 20251001, 'Login', 'User logged in: axcee', '2025-04-21 15:26:39');

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
(1, 'Bachelor of Science in Information Technology', 'College of Information Technology', 'BSIT', 'Inactive'),
(2, 'Bachelor of Science in Hospitality Management', 'College of Hospitality Management', 'BSHM', 'Active'),
(3, 'Bachelor of Science in Education', 'College of Education', 'BSED', 'Inactive'),
(4, 'Bachelor of Science in Criminology', ' College of Criminology', 'BSCRIM', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `prospectus`
--

CREATE TABLE `prospectus` (
  `pr_id` int(11) NOT NULL,
  `program_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `year_level` varchar(225) DEFAULT NULL,
  `semester` varchar(225) DEFAULT NULL,
  `pr_effective_year` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prospectus`
--

INSERT INTO `prospectus` (`pr_id`, `program_id`, `course_id`, `year_level`, `semester`, `pr_effective_year`, `status`, `created_by`) VALUES
(6, 1, 3, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(7, 1, 3, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(8, 1, 4, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(9, 1, 4, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(10, 1, 5, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(11, 1, 5, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(12, 1, 6, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(13, 1, 6, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(14, 1, 7, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(15, 1, 7, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(16, 1, 8, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(17, 1, 8, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(18, 1, 9, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(19, 1, 9, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(20, 1, 10, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(21, 1, 10, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(22, 1, 11, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(23, 1, 11, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(24, 1, 12, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(25, 1, 12, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(26, 1, 13, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(27, 1, 13, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(28, 1, 14, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001),
(29, 1, 14, '1st Year', '1st Semester', '2023 - 2024', 'Active', 20251001);

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
(4, 20251001, 'Axcee', 'Filisilda', 'Cabusas', '2004-09-06', 'Tunghaan', 'Male', 4, 1, 'SCC23', 'src/prospectus/images/students/default-user.png'),
(5, 20251020, 'Mica', '', 'Lariosa', '1990-01-05', 'Minglanilla', 'Female', 4, 3, 'SCC', 'src/prospectus/images/student/default-user.png');

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
(20251001, 'Axcee', 'Felisilda', 'Cabusas', 'axceelfelis03@gmail.com', '99140820611', 'axcee', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'Admin', 'Active', 'Enrolled', '1234', 'src/prospectus/images/users/default-user.png'),
(20251017, 'Rasheed', '', 'Tapales', 'rashed@gmail.com', '98273645671', 'rashed', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'User', 'Active', 'Not Enrolled', 'jungle sunflower waterfall grape volcano sunflower umbrella tiger apple banana grape mountain', 'src/prospectus/images/users/default-user.png'),
(20251018, 'Adrianne Lhoue', '', 'Inso', 'inso12@gmail.com', '99872635678', 'inso', 'ZMI04LI4wpHFQpAFMuXoV2yxsvqk4QMRbYUIAql7DWI=', 'User', 'Active', 'Not Enrolled', 'elephant waterfall jungle kangaroo banana jungle dog flower waterfall grape waterfall queen', 'src/prospectus/images/users/received_178815621514415.jpg'),
(20251019, 'Aljon', '', 'Paragoso', 'aljon@gmail.com', '9872634567', 'aljon', '4Sbb5S0/5dD/PgMPKbxnvhdqH4oUlAoorkqbhnsOiVY=', 'Student', 'Active', 'Not Enrolled', 'jungle banana volcano tiger zebra grape house queen xylophone flower xylophone waterfall', 'src/prospectus/images/users/Screenshot_1.png'),
(20251020, 'Mica', '', 'Lariosa', 'mica@gmail.com', '9876153789', 'qwerty', 'TU8mNpFxmU86Rndu4tiElPuZVYAKW7YmHAFsS7nzC1Y=', 'Student', 'Active', 'Enrolled', 'zebra jungle xylophone queen kangaroo umbrella yellow mountain penguin tiger cherry apple', 'src/prospectus/images/users/default-user.png');

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
  ADD KEY `course_id` (`course_id`) USING BTREE,
  ADD KEY `created_by` (`created_by`);

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
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `enrollment`
--
ALTER TABLE `enrollment`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=431;

--
-- AUTO_INCREMENT for table `program`
--
ALTER TABLE `program`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `prospectus`
--
ALTER TABLE `prospectus`
  MODIFY `pr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
  ADD CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`u_id`),
  ADD CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`prog_id`) REFERENCES `program` (`p_id`);

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
  ADD CONSTRAINT `prospectus_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `program` (`p_id`),
  ADD CONSTRAINT `prospectus_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `user` (`u_id`);

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
