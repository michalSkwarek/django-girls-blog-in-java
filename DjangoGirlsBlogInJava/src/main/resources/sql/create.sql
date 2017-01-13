-- ----------------------------------------------
-- add few users
INSERT INTO user (id, username, password, enabled, role) VALUES (1, 'Michal', 'michal', TRUE, 'ROLE_ADMIN');
INSERT INTO user (id, username, password, enabled, role) VALUES (2, 'Radek', 'radek', TRUE, 'ROLE_ADMIN');

-- add few posts
INSERT INTO post (id, author_id, title, text, created_date, published_date) VALUES (1, 1, 'Multithreading benefits: better resource utilization', 'Imagine an application that reads and processes files from the local file system. Lets say that reading af file from disk takes 5 seconds and processing it takes 2 seconds. When reading the file from disk most of the CPU time is spent waiting for the disk to read the domain. The CPU is pretty much idle during that time. It could be doing something else. By changing the order of the operations, the CPU could be better utilized. The CPU waits for the first file to be read. Then it starts the read of the second file. While the second file is being read, the CPU processes the first file. Remember, while waiting for the file to be read from disk, the CPU is mostly idle. In general, the CPU can be doing other things while waiting for IO. It doesn''t have to be disk IO. It can be network IO as well, or input from a user at the machine. Network and disk IO is often a lot slower than CPU''s and memory IO.', STR_TO_DATE('01-01-2017 15:31:55', '%d-%m-%Y %H:%i:%s'), STR_TO_DATE('02-01-2017 15:55:55', '%d-%m-%Y %H:%i:%s'));
INSERT INTO post (id, author_id, title, text, created_date, published_date) VALUES (2, 1, 'Multithreading benefits: simpler program design', 'If you were to program the above ordering of reading and processing by hand in a singlethreaded application, you would have to keep track of both the read and processing state of each file. Instead you can start two threads that each just reads and processes a single file. Each of these threads will be blocked while waiting for the disk to read its file. While waiting, other threads can use the CPU to process the parts of the file they have already read. The result is, that the disk is kept busy at all times, reading from various files into memory. This results in a better utilization of both the disk and the CPU. It is also easier to program, since each thread only has to keep track of a single file.', STR_TO_DATE('02-01-2017 02:31:55', '%d-%m-%Y %H:%i:%s'), STR_TO_DATE('02-01-2017 16:01:55', '%d-%m-%Y %H:%i:%s'));
INSERT INTO post (id, author_id, title, text, created_date, published_date) VALUES (3, 1, 'Multithreading benefits: more responsive programs', 'Another common goal for turning a singlethreaded application into a multithreaded application is to achieve a more responsive application. Imagine a server application that listens on some port for incoming requests. when a request is received, it handles the request and then goes back to listening. ', STR_TO_DATE('03-01-2017 15:01:05', '%d-%m-%Y %H:%i:%s'), STR_TO_DATE('04-01-2017 17:31:55', '%d-%m-%Y %H:%i:%s'));
INSERT INTO post (id, author_id, title, text, created_date) VALUES (4, 2, 'Multithreading costs: more complex design', 'Though some parts of a multithreaded applications is simpler than a singlethreaded application, other parts are more complex. Code executed by multiple threads accessing shared domain need special attention. Thread interaction is far from always simple. Errors arising from incorrect thread synchronization can be very hard to detect, reproduce and fix.', STR_TO_DATE('04-01-2017 13:31:55', '%d-%m-%Y %H:%i:%s'));
INSERT INTO post (id, author_id, title, text, created_date) VALUES (5, 2, 'Multithreading costs: context switching overhead', 'When a CPU switches from executing one thread to executing another, the CPU needs to save the local domain, program pointer etc. of the current thread, and load the local domain, program pointer etc. of the next thread to execute. This switch is called a "context switch". The CPU switches from executing in the context of one thread to executing in the context of another.', STR_TO_DATE('05-01-2017 19:31:55', '%d-%m-%Y %H:%i:%s'));

-- add few comments
INSERT INTO comment (id, author, text, created_date, approved_comment, post_id) VALUES (1, 'Mohanty', 'Thanks. You have given valuable time which helps a lot.', STR_TO_DATE('02-01-2017 18:22:33', '%d-%m-%Y %H:%i:%s'), TRUE, 1);
INSERT INTO comment (id, author, text, created_date, approved_comment, post_id) VALUES (2, 'philip', 'Your thoughts are clear on this thread information - thanks.', STR_TO_DATE('02-01-2017 18:34:35', '%d-%m-%Y %H:%i:%s'), TRUE, 1);
INSERT INTO comment (id, author, text, created_date, approved_comment, post_id) VALUES (3, 'Lupe', 'One of the best tutorials I''ve read on the net, thank you so much for these Jenkov, much appreciated.', STR_TO_DATE('04-01-2017 12:23:13', '%d-%m-%Y %H:%i:%s'), FALSE, 1);
INSERT INTO comment (id, author, text, created_date, approved_comment, post_id) VALUES (4, 'shine', 'Thanks a lot. Really good articles.', STR_TO_DATE('04-01-2017 18:01:02', '%d-%m-%Y %H:%i:%s'), FALSE, 1);
INSERT INTO comment (id, author, text, created_date, approved_comment, post_id) VALUES (5, 'Author1', 'Thank you for your''s tutorials.', STR_TO_DATE('02-01-2017 17:23:23', '%d-%m-%Y %H:%i:%s'), TRUE, 2);