-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 30, 2020 at 07:10 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cnc`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `aid` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `loginTime` time DEFAULT NULL,
  `logoutTime` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`aid`, `password`, `loginTime`, `logoutTime`) VALUES
('admin', '123', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `attendence`
--

CREATE TABLE `attendence` (
  `no` int(11) NOT NULL,
  `sid` varchar(30) DEFAULT NULL,
  `cid` varchar(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `slot1` varchar(30) DEFAULT NULL,
  `slot2` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `attendence`
--

INSERT INTO `attendence` (`no`, `sid`, `cid`, `date`, `slot1`, `slot2`) VALUES
(21, '1', 'CS1232', '2020-01-17', '1', '0');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `cid` varchar(30) NOT NULL,
  `cname` varchar(30) NOT NULL,
  `ctype` varchar(30) NOT NULL,
  `depid` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`cid`, `cname`, `ctype`, `depid`) VALUES
('CS1020', 'Computer Science', 'GPA', ''),
('CS1030', 'Computer Engineering', 'GPA', ''),
('CS1232', 'DSA', 'GPA', ''),
('NCS1432', 'English', 'NGPA', '');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentNo` int(11) NOT NULL,
  `sid` varchar(30) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(30) DEFAULT NULL,
  `semester` varchar(30) DEFAULT NULL,
  `amount` varchar(30) DEFAULT NULL,
  `paymentDate` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentNo`, `sid`, `fname`, `lname`, `semester`, `amount`, `paymentDate`) VALUES
(1, '1', 'Chamath', 'Wanigasooriya', '4', '1234', '2020-01-11'),
(2, '2', 'Sir.Budditha', 'Hettige', '1', '1234', '2020-01-09');

-- --------------------------------------------------------

--
-- Table structure for table `register`
--

CREATE TABLE `register` (
  `regNo` int(11) NOT NULL,
  `fname` varchar(30) DEFAULT NULL,
  `lName` varchar(30) DEFAULT NULL,
  `nic` varchar(30) DEFAULT NULL,
  `dob` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `course` varchar(30) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `register`
--

INSERT INTO `register` (`regNo`, `fname`, `lName`, `nic`, `dob`, `email`, `course`, `phone`) VALUES
(14, 'Chamath', 'Wanigasooriya', '12345678', '1993-1-4', 'chamathwanigasooriya@gmail.com', 'Computer Science', '1234567890');

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `indexNo` int(11) NOT NULL,
  `sid` varchar(11) DEFAULT NULL,
  `cid` varchar(30) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `grade` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`indexNo`, `sid`, `cid`, `name`, `grade`) VALUES
(12, '2', 'CS1020', 'Sir.Budditha Hettige', 'A+');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` varchar(30) NOT NULL,
  `fname` varchar(30) DEFAULT NULL,
  `lname` varchar(30) DEFAULT NULL,
  `nic` varchar(30) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `tele` varchar(30) DEFAULT NULL,
  `cid` varchar(30) DEFAULT NULL,
  `userName` varchar(30) DEFAULT NULL,
  `password` varchar(30) NOT NULL DEFAULT 'qwerty123',
  `dp` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `fname`, `lname`, `nic`, `dob`, `email`, `tele`, `cid`, `userName`, `password`, `dp`) VALUES
('1', 'Chamath', 'Wanigasooriya', '12345678', '1990-01-01', 'chamathwanigasooriya@gmail.com', '0147852369', 'CS1020', NULL, 'qwerty123', NULL),
('2', 'Sir.Budditha', 'Hettige', '12345678', '1990-01-01', 'abc@gmail.com', '0147852369', 'CS1020', NULL, 'qwerty123', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`aid`);

--
-- Indexes for table `attendence`
--
ALTER TABLE `attendence`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentNo`);

--
-- Indexes for table `register`
--
ALTER TABLE `register`
  ADD PRIMARY KEY (`regNo`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`indexNo`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendence`
--
ALTER TABLE `attendence`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `register`
--
ALTER TABLE `register`
  MODIFY `regNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `result`
--
ALTER TABLE `result`
  MODIFY `indexNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
