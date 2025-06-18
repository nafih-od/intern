/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - educational_resource_management_system_2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`educational_resource_management_system_2` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `educational_resource_management_system_2`;

/*Table structure for table `assessment` */

DROP TABLE IF EXISTS `assessment`;

CREATE TABLE `assessment` (
  `assessment_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`assessment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `assessment` */

insert  into `assessment`(`assessment_id`,`teacher_id`,`title`,`description`,`date`) values 
(1,1,'set1','sdfghjk','2025-03-02'),
(2,1,'set2','dfghj','2025-03-02');

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `sender_type` varchar(100) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `receiver_type` varchar(100) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`chat_id`,`sender_id`,`sender_type`,`receiver_id`,`receiver_type`,`message`,`date`,`time`) values 
(1,2,'teacher',5,'student','hi','2025-02-28','15:35:17'),
(2,2,'teacher',5,'student','this is your teacher here','2025-02-28','15:36:28'),
(3,2,'teacher',5,'student','can you send me your address ','2025-02-28','15:36:44'),
(4,2,'teacher',5,'student','hlooo','2025-02-28','15:40:58'),
(5,2,'teacher',5,'student','are you there?','2025-02-28','15:41:34'),
(6,2,'teacher',5,'student','OK i will call you','2025-02-28','15:41:52'),
(7,5,'student',2,'teacher','oke mam','2025-03-02','23:20:38');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`sender_id`,`complaint`,`reply`,`date`) values 
(1,1,'not working','will check','2025-02-28'),
(2,1,'can you fix the laging','pending','2025-02-28'),
(3,5,'ndjd','pending','2025-03-03');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `course` */

insert  into `course`(`course_id`,`course`) values 
(1,'BTech'),
(2,'BSC'),
(3,'BCA');

/*Table structure for table `lecture_note` */

DROP TABLE IF EXISTS `lecture_note`;

CREATE TABLE `lecture_note` (
  `note_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `file` varchar(200) DEFAULT NULL,
  `summarization` varchar(500) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`note_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `lecture_note` */

insert  into `lecture_note`(`note_id`,`teacher_id`,`title`,`file`,`summarization`,`date`,`type`) values 
(1,1,'note 1','static/lecture_note_video/8d995e40-69ac-4734-84b6-266589072938WIN_20250301_09_39_41_Pro.mp4','pending','2025-03-01',NULL),
(2,1,'sdf','static/lecture_note_video/40a46803-0c76-4e8c-871b-c09e0fef6d5eWIN_20250301_09_39_41_Pro.mp4','pending','2025-03-01',NULL),
(3,1,'ooo','static/lecture_note_video/9b5af141-0d7e-43d2-a8f1-974d8c1f48c4WhatsApp Video 2025-03-01 at 13.33.16_2393ee30.mp4','pending','2025-03-01',NULL),
(4,1,'qqqqq','static/lecture_note_video/c99e5051-acf1-4fe7-9be0-eb72c8fff74aWhatsApp Video 2025-03-01 at 13.33.16_2393ee30.mp4','in education there are three system one is primary education Secondary Education higher education ok now I am not much concerned the secondary education the higher education but our primary education is very bad that mean the children of creativity up to age 17 the education should bring out the creativity of the children that mean the teacher has been creative the classroom has been created','2025-03-01',NULL),
(5,1,'Android','static/lecture_note_video/4c010ff1-2c51-41f7-8f82-adf6e0027dbcWhatsApp Video 2025-03-01 at 13.33.16_2393ee30.mp4','in education there are three system one is primary education Secondary Education higher education ok now I am not much concerned the secondary education the higher education but our primary education is very bad that mean the children of creativity up to age 17 the education should bring out the creativity of the children that mean the teacher has been creative the classroom has been created','2025-03-01',NULL),
(6,1,'sssss','static/f72fe863-a3ff-4ecb-8bd6-f84fd2740ca4calendar (1).png','','2025-03-13','image'),
(7,1,'mmmmmmmmm','static/92a20916-f512-4509-9c94-ffc7cd78ed60Crop_yield_prediction_1.pdf','','2025-03-13','pdf'),
(8,1,'mmmmmmmmm','static/fd9e40b2-9d05-4ca4-bf8f-208a0dffed8bWhatsApp Video 2025-03-01 at 13.33.16_496322ba.mp4','in education there are three system one is primary education Secondary Education higher education ok now I am not much concerned the secondary education the higher education but our primary education is very bad that mean the children of creativity up to age 17 the education should bring out the creativity of the children that mean the teacher has been creative the classroom has been created','2025-03-13','video');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'ttt','ttt@123','teacher'),
(3,'rrr','rrr@123','teacher'),
(4,'ddd','ddd@123','teacher'),
(5,'sss','sss','student'),
(6,'aa1','aa@123','student'),
(7,'ASD','asd@1234','student'),
(8,'aaaaaaaa','aaaaaaaa','teacher'),
(9,'teacher','asdfgbn!12345y','teacher'),
(11,'sdfgb','sdfgasdxcfv@1','student');

/*Table structure for table `previous_question_paper` */

DROP TABLE IF EXISTS `previous_question_paper`;

CREATE TABLE `previous_question_paper` (
  `qp_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL,
  `year` varchar(100) DEFAULT NULL,
  `file` varchar(500) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`qp_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `previous_question_paper` */

insert  into `previous_question_paper`(`qp_id`,`subject_id`,`year`,`file`,`date`) values 
(1,1,'2018','static/430cae46-864b-4619-a307-749cb475f627Crop_yield_prediction (1).pdf','2025-02-28');

/*Table structure for table `question_answer` */

DROP TABLE IF EXISTS `question_answer`;

CREATE TABLE `question_answer` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `assessment_id` int(11) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL,
  `option_1` varchar(100) DEFAULT NULL,
  `option_2` varchar(100) DEFAULT NULL,
  `option_3` varchar(100) DEFAULT NULL,
  `answer` varchar(100) DEFAULT NULL,
  `score` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `question_answer` */

insert  into `question_answer`(`question_id`,`assessment_id`,`question`,`option_1`,`option_2`,`option_3`,`answer`,`score`) values 
(1,1,'ppp','a','b','c','d','2'),
(2,1,'qqq','g','h','j','k','2'),
(3,2,'asdf','sdfv','df','asdf','vvv','3');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `sem` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `student` */

insert  into `student`(`student_id`,`login_id`,`teacher_id`,`course_id`,`sem`,`name`,`place`,`phone`,`email`) values 
(1,5,1,1,'S1','Sinta','Thrissur','8222333448','s@gmail.com'),
(2,6,1,2,'S2','Arun','Thrissur','8234333444','df@gmail.com'),
(3,7,1,1,'S1','asd','Thrissur','8908908908','vvvvvvvvv@gmail.com'),
(4,11,1,1,'S1','ddd ddd','dfghj','9876854444','wefz@gmail.com');

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `subject` */

insert  into `subject`(`subject_id`,`subject`) values 
(1,'Compiler Design'),
(2,'Computer Science'),
(3,'Electronics'),
(4,'Physics'),
(5,'Chemistry');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `teacher` */

insert  into `teacher`(`teacher_id`,`login_id`,`subject_id`,`name`,`place`,`phone`,`email`) values 
(1,2,1,'Ananya','Thrissur','9087678938','ana@gamil.com'),
(2,3,2,'Rijo','Palakkad','9888766545','ri@gmail.com'),
(3,4,4,'David','Kollam','8789898989','d@gmail.com'),
(4,8,2,'ddd ddd','thrissur','01234567890','wefz@gmail.com'),
(5,9,2,'ddd ddd','thrissur','01234567890','wefz@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
