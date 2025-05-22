-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2025 at 01:21 PM
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
-- Database: `rs`
--

-- --------------------------------------------------------

--
-- Table structure for table `chief`
--

CREATE TABLE `chief` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `pasword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chief`
--

INSERT INTO `chief` (`id`, `fullname`, `username`, `email`, `pasword`) VALUES
(1, 'Edelawit Huluwork', 'edel', 'edel21@gmail.com', 'edel');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `pasword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `fullname`, `username`, `email`, `pasword`) VALUES
(1, 'Hermella Ashagere', 'hermi', 'fe21kir@gmail.com', 'hermi'),
(2, 'Ekram Tofik', 'ekram', 'ekru21@gmail.com', 'ekram'),
(3, 'Haymanot Alemayehu', 'haymi', 'haymi21@gmail.com', 'haymi');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `message` text NOT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`id`, `customer_id`, `message`, `submitted_at`) VALUES
(1, 1, 'good menu', '2025-05-19 21:45:02'),
(2, 1, 'overall it is nice. if there is variety of coffee it will be good', '2025-05-19 21:47:02'),
(3, 1, 'what a nice service', '2025-05-19 21:51:41'),
(4, 1, 'wow', '2025-05-20 08:56:41');

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pasword` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manager`
--

INSERT INTO `manager` (`id`, `name`, `username`, `pasword`) VALUES
(1, 'Mahlet', 'mahi', 'mahi'),
(2, 'Lidya', 'lidu', 'lidu');

-- --------------------------------------------------------

--
-- Table structure for table `menu_items`
--

CREATE TABLE `menu_items` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `category` enum('Breakfast','Lunch','Desserts','Drinks') NOT NULL,
  `image_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_items`
--

INSERT INTO `menu_items` (`id`, `name`, `description`, `price`, `category`, `image_path`) VALUES
(1, 'Breakfast Platter', NULL, 9.99, 'Breakfast', 'RS/UI/breakfast/B_platter.jpg'),
(2, 'Breakfast Wraps', NULL, 7.99, 'Breakfast', 'RS/UI/breakfast/B_wraps.jpg'),
(3, 'Breakfast Sandwiches', NULL, 6.99, 'Breakfast', 'RS/UI/breakfast/B_sandwiches.jpg'),
(4, 'Breakfast Waffles', NULL, 8.99, 'Breakfast', 'RS/UI/breakfast/B_waffles.jpg'),
(5, 'Breakfast Omelets', NULL, 8.49, 'Breakfast', 'RS/UI/breakfast/B_omelets.jpg'),
(6, 'Breakfast Fullpack', NULL, 5.99, 'Breakfast', 'RS/UI/breakfast/B_fullpack.jpg'),
(7, 'Burger', 'Beef burger with cheese', 33.00, 'Lunch', NULL),
(8, 'Spiced carrot and lentil soup', NULL, 33.00, 'Lunch', NULL),
(9, 'Amareto cake', NULL, 33.00, 'Lunch', NULL),
(10, 'Mushroom Spaghetti ', NULL, 39.00, 'Lunch', NULL),
(11, 'coconut kale chicken salad', NULL, 39.00, 'Lunch', NULL),
(12, 'cinnamon roll pancake', NULL, 39.00, 'Lunch', NULL),
(13, 'Cheese cake', NULL, 7.00, 'Desserts', NULL),
(14, 'Tiramisu', NULL, 10.00, 'Desserts', NULL),
(15, 'French Gelato', NULL, 6.00, 'Desserts', NULL),
(16, 'Triple layer cake', NULL, 10.00, 'Desserts', NULL),
(17, 'Apple fritters', NULL, 7.00, 'Desserts', NULL),
(18, 'Skillet Brownie', NULL, 9.00, 'Desserts', NULL),
(19, 'Dessert Sampler', NULL, 25.00, 'Desserts', NULL),
(20, 'Bloody mary', NULL, 6.75, 'Drinks', NULL),
(21, 'Margarita', NULL, 8.75, 'Drinks', NULL),
(22, 'old fashioned', NULL, 7.75, 'Drinks', NULL),
(23, 'cosmopolitan', NULL, 8.00, 'Drinks', NULL),
(24, 'mojito', NULL, 7.50, 'Drinks', NULL),
(25, 'lemon drop', NULL, 8.00, 'Drinks', NULL),
(26, 'irish coffee', NULL, 8.50, 'Drinks', NULL),
(27, 'endless summer', NULL, 9.00, 'Drinks', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pasword` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`id`, `name`, `username`, `pasword`) VALUES
(1, 'KERNEMI', 'keri', 'keri');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chief`
--
ALTER TABLE `chief`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `menu_items`
--
ALTER TABLE `menu_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chief`
--
ALTER TABLE `chief`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `manager`
--
ALTER TABLE `manager`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `menu_items`
--
ALTER TABLE `menu_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `owner`
--
ALTER TABLE `owner`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
