CREATE DATABASE jdbc DEFAULT CHARACTER SET 'UTF8';

USE jdbc;

CREATE TABLE `message` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`uid` INT ( 11 ) NOT NULL,
	`username` VARCHAR ( 20 ) NOT NULL,
	`title` VARCHAR ( 32 ) NOT NULL,
	`content` VARCHAR ( 4096 ) NOT NULL,
	`create_time` datetime NOT NULL,
	PRIMARY KEY ( `id` )
) ENGINE = INNODB CHARSET = UTF8;

INSERT `message`
VALUES
	( NULL, 10000, 'admin', 'JAVA', '<span clolor=\'red\'> Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。</span>', '2019-03-01 01:17:23' ),
	( NULL, 10001, 'jack', 'PHP', '<span clolor=\'red\'> PHP是一门面向对象编程语言，是一种通用开源脚本语言。语法吸收了C语言、Java和Perl的特点，利于学习，使用广泛，主要适用于Web开发领域。PHP 独特的语法混合了C、Java、Perl以及PHP自创的语法</span>', '2019-03-02 11:17:23' ),
	( NULL, 10002, 'rose', 'C', '<span clolor=\'red\'> C语言是一门面向过程、抽象化的通用程序设计语言，广泛应用于底层开发。</span>', '2019-03-03 01:17:23' ),
	( NULL, 10003, 'lily', 'C#', '<span clolor=\'red\'> C#是微软公司发布的一种面向对象的、运行于.NET Framework和.NET Core(完全开源，跨平台)之上的高级程序设计语言。</span>', '2019-02-01 00:17:23' ),
	( NULL, 10004, 'lucy', 'C++', '<span clolor=\'red\'> C++是C语言的继承,它既可以进行C语言的过程化程序设计,又可以进行以抽象数据类型为特点的基于对象的程序设计,还可以进行以继承和多态为特点的面向对象...</span>', '2019-03-01 01:17:23' ),
	( NULL, 10005, 'john', 'Lua', '<span clolor=\'red\'> Lua 是一个小巧的脚本语言。它是巴西里约热内卢天主教大学里的一个由Roberto Ierusalimschy、Waldemar Celes 和 Luiz Henrique de Figueiredo三人所组成的研究小组于1993年开发的。</span>', '2018-03-01 01:30:23' ),
	( NULL, 10006, '韩梅梅', 'HTML', '<span clolor=\'red\'> 超文本标记语言，标准通用标记语言下的一个应用。是 网页制作必备的编程语言“超文本”就是指页面内可以包含图片、链接，甚至音乐、程序等非文字元素。</span>', '2018-12-01 01:17:23' ),
	( NULL, 10007, '李雷', 'CSS', '<span clolor=\'red\'> 层叠样式表,英文全称：Cascading Style Sheets 是一种用来表现HTML或XML等文件样式的计算机语言</span>', '2018-04-01 10:17:23' ),
	( NULL, 10008, '张三', 'JavaScript', '<span clolor=\'red\'> Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。</span>', '2018-02-01 09:17:23' ),
	( NULL, 10009, '张三丰', 'NodeJs', '<span clolor=\'red\'> JavaScript一种直译式脚本语言，是一种动态类型、弱类型、基于原型的语言，内置支持类型。它的解释器被称为JavaScript引擎，为浏览器的一部分，广泛用于客户端的脚本语言</span>', '2017-03-01 01:17:23' ),
	( NULL, 10010, '李四', 'VUE', '<span clolor=\'red\'> Vue.js - The Progressive JavaScript Framework... Vue 是一套用于构建用户界面的渐进式框架。</span>', '2018-10-01 01:17:23' ),
	( NULL, 10011, '王二妹', 'jQuery', '<span clolor=\'red\'> jQuery是一个快速、简洁的JavaScript框架，是继Prototype之后又一个优秀的JavaScript代码库。</span>', '2018-06-01 01:17:23' ),
	( NULL, 10012, '王小丫', 'shell', '<span clolor=\'red\'> Shell俗称壳（用来区别于核），是指“为使用者提供操作界面”的软件,它类似于DOS下的command.com和后来的cmd.exe</span>', '2018-11-01 01:17:23' ),
	( NULL, 10013, '赵柳', 'apache', '<span clolor=\'red\'> Apache是世界使用排名第一的Web服务器软件。它可以运行在几乎所有广泛使用的计算机平台上，由于其跨平台和安全性被广泛使用，是最流行的Web服务器端软件之一。</span>', '2015-12-01 01:17:23' ),
	( NULL, 10014, '郭富城', 'Nginx', '<span clolor=\'red\'> Nginx 是一个高性能的HTTP和反向代理服务</span>', '2019-01-01 01:17:23' ),
	( NULL, 10015, '刘德华', 'Tomcat', '<span clolor=\'red\'> Tomcat是由Apache软件基金会下属的Jakarta项目开发的一个Servlet容器，按照Sun Microsystems提供的技术规范，实现了对Servlet和JavaServer Page的支持，并提供了作为Web服务器的一些特有功能，如Tomcat管理和控制平台、安全域管理和Tomcat阀等.</span>', '2019-01-03 01:17:23' ),
	( NULL, 10016, '张学友', 'Git', '<span clolor=\'red\'> Git是一个开源的分布式版本控制系统，可以有效、高速地处理从很小到非常大的项目版本管理。Git 是 Linus Torvalds 为了帮助管理 Linux 内核开发而开发的一个开放源码的版本控制软件。</span>', '2019-02-21 01:17:23' ),
	( NULL, 10017, '黎明', 'SVN', '<span clolor=\'red\'> SVN 是Subversion的简称，是一个开放源代码的版本控制系统，相较于RCS、CVS，它采用了分支管理系统，它的设计目标就是取代CVS。。</span>', '2019-02-11 01:17:23' ),
	( NULL, 10018, '金大朗', 'Docker', '<span clolor=\'red\'> Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的容器中，然后发布到任何流行的 Linux 机器上，也可以实现虚拟化。</span>', '2018-12-01 01:17:23' ),
	( NULL, 10019, '金二郎', 'WebServer', '<span clolor=\'red\'> Web Server中文名称叫网页服务器或web服务器。WEB服务器也称为WWW(WORLD WIDE WEB)服务器，主要功能是提供网上信息浏览服务</span>', '2019-01-11 01:17:23' ),
	( NULL, 10020, '顾廷烨', 'Python', '<span clolor=\'red\'> Python是一种计算机程序设计语言。是一种动态的、面向对象的脚本语言，最初被设计用于编写自动化脚本，随着版本的不断更新和语言新功能的添加，越来越多被用于独立的、大型项目的开发。</span>', '2016-03-01 01:17:23' ),
	( NULL, 10021, '盛明兰', 'Golang', '<span clolor=\'red\'> Go 又称 Golang 是Google开发的一种静态强类型、编译型、并发型，并具有垃圾回收功能的编程语言。</span>', '2018-12-01 01:17:23' ),
	( NULL, 10022, '盛如兰', 'Swift', '<span clolor=\'red\'>Swift，苹果于2014年WWDC发布的新开发语言，可与Objective-C*共同运行于Mac OS和iOS平台，用于搭建基于苹果平台的应用程序</span>', '2017-03-01 01:17:23' );


CREATE TABLE `user` (
		`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
		`username` varchar(20) NOT NULL COMMENT '用户名',
		`password` char(32) NOT NULL COMMENT '密码',
		`real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
		`sex` enum('男','女','保密') NOT NULL DEFAULT '保密' COMMENT '性别(m:男, f:女, u:保密)',
		`birthday` date DEFAULT NULL COMMENT '生日',
		`phone` char(11) DEFAULT NULL COMMENT '电话',
		`address` varchar(50) NOT NULL DEFAULT '' COMMENT '地址',
		`photo` varchar(40) NOT NULL DEFAULT '' COMMENT '头像',
		`interest` varchar(10) NOT NULL DEFAULT '' COMMENT '兴趣(0:前端，1:后端，2:架构)',
		`created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
		`updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
		PRIMARY KEY (`id`),
		UNIQUE KEY `user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


INSERT INTO `user`
VALUES (NULL, 'admin', 'admin', '侯亮平', 'm', '1989-01-01 00:00:00', '15012345678', '三清观2-1', '', '0,1', NOW(), NOW());

ALTER TABLE `jdbc`.`user` ADD UNIQUE INDEX `user_phone`(`phone`) USING BTREE;