-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 17, 2025 at 03:00 PM
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
  `prerequisite_id` int(11) DEFAULT NULL,
  `program_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`c_id`, `c_code`, `c_desc`, `c_units`, `prerequisite_id`, `program_id`) VALUES
(1, 'HCI101', 'Human Computer Interaction', 5, NULL, 3),
(2, 'CC101', 'Introduction to Computing', 5, NULL, 1);

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
(6, 20251018, 2, '2025-04-17 12:44:11');

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
(306, 20251001, 'Login', 'User logged in: axceead', '2025-04-17 13:00:31');

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
(3, 'Bachelor of Science in Education', 'College of Education', 'BSED', 'Active');

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
  `u_id` int(11) NOT NULL,
  `s_fname` varchar(255) NOT NULL,
  `s_mname` varchar(255) NOT NULL,
  `s_lname` varchar(255) NOT NULL,
  `s_bdate` date NOT NULL,
  `s_address` text NOT NULL,
  `s_year` int(11) NOT NULL,
  `s_program` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`s_id`, `u_id`, `s_fname`, `s_mname`, `s_lname`, `s_bdate`, `s_address`, `s_year`, `s_program`) VALUES
(1, 20251018, 'Adrianne Lhoue', '', 'Inso', '2003-04-10', 'Tungkop Minglanilla', 3, '2');

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
(20251001, 'Axcee', 'Felisilda', 'Cabusas', 'axceelfelis03@gmail.com', '99140820611', 'axceead', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'Admin', 'Active', 'Not Enrolled', '1234', 'src/prospectus/images/users/waw.jpg'),
(20251017, 'rashed', 'wahaha', 'rashed', 'rashed@gmail.com', '98273645671', 'rashed', 'fRm8kPPm6HMqAiRuWC3AzkiYLvVFYqLcpR/PK71tMww=', 'User', 'Active', 'Not Enrolled', 'jungle sunflower waterfall grape volcano sunflower umbrella tiger apple banana grape mountain', 'src/prospectus/images/users/5c35e123-87e1-4bff-b6f8-d5e9489bbf5a.jpg'),
(20251018, 'addrianne', 'sam', 'inso', 'inso12@gmail.com', '99872635678', 'inso', 'ZMI04LI4wpHFQpAFMuXoV2yxsvqk4QMRbYUIAql7DWI=', 'Student', 'Active', 'Enrolled', 'elephant waterfall jungle kangaroo banana jungle dog flower waterfall grape waterfall queen', 'src/prospectus/images/users/received_178815621514415.jpg'),
(20251019, 'Aljon', '', 'Paragoso', 'aljon@gmail.com', '9872634567', 'aljon', '4Sbb5S0/5dD/PgMPKbxnvhdqH4oUlAoorkqbhnsOiVY=', 'User', 'Active', 'Not Enrolled', 'jungle banana volcano tiger zebra grape house queen xylophone flower xylophone waterfall', 'src/prospectus/images/users/Screenshot_1.png'),
(20251020, 'Panot', 'ERT', 'Panot', 'QWER@gmail.com', '9876153789', 'qwerty', 'TU8mNpFxmU86Rndu4tiElPuZVYAKW7YmHAFsS7nzC1Y=', 'User', 'Active', 'Not Enrolled', 'zebra jungle xylophone queen kangaroo umbrella yellow mountain penguin tiger cherry apple', 'src/prospectus/images/users/default-user.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`c_id`),
  ADD KEY `prerequisite_id` (`prerequisite_id`),
  ADD KEY `program_id` (`program_id`);

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
  ADD KEY `course_id` (`course_id`) USING BTREE;

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`s_id`),
  ADD KEY `u_id` (`u_id`) USING BTREE;

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
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `enrollment`
--
ALTER TABLE `enrollment`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=307;

--
-- AUTO_INCREMENT for table `program`
--
ALTER TABLE `program`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `prospectus`
--
ALTER TABLE `prospectus`
  MODIFY `pr_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`prerequisite_id`) REFERENCES `course` (`c_id`),
  ADD CONSTRAINT `course_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `program` (`p_id`);

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
  ADD CONSTRAINT `prospectus_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `program` (`p_id`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
