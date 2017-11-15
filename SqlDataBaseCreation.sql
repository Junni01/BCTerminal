
CREATE TABLE Workers(
   WorkerID      INTEGER      PRIMARY KEY,
   Lname          VARCHAR(32),
   Fname      VARCHAR(32),
   JobInProgress        BOOLEAN,
   BadgeNum     INTEGER
);

CREATE TABLE Jobs (
   JobID    INTEGER      PRIMARY KEY,
   JobName       VARCHAR(64),
   Finished      BOOLEAN,
   Paused        BOOLEAN,
   TotalTime     INTEGER,
   OnHoldTime	   INTEGER,
   StartTime	   DATETIME,
   EndTime	   DATETIME,
   PauseTime	   DATETIME
);

CREATE TABLE Project (
   ProjectID    INTEGER      PRIMARY KEY,
   ProjName       VARCHAR(64),
   TotalHours     INTEGER,
   Finished	   BOOLEAN,
   StartTime	   DATETIME,
   EndTime	   DATETIME
);

CREATE TABLE ProjectJob (
   ProjJobID      INTEGER,
   ProjectID 		INTEGER,
   JobID    INTEGER,
  
   PRIMARY KEY(ProjJobID),

   FOREIGN KEY(ProjectID) REFERENCES Project(ProjectID),
   FOREIGN KEY(JobID) REFERENCES Jobs(JobID)
);

CREATE TABLE WorkerJob (
   WorkerID      INTEGER,
   JobID    INTEGER,
  
   PRIMARY KEY(WorkerID, JobID),

   FOREIGN KEY(WorkerID) REFERENCES Workers(WorkerID),
   FOREIGN KEY(JobID) REFERENCES Jobs(JobID)
);

INSERT INTO "jobs"
   VALUES (1, 'CUTTING', 0, 0, 0, 0, NULL, NULL, NULL);
   
INSERT INTO "jobs"
   VALUES (2, 'WELDING', 0, 0, 6, 0, '2017-10-12 08:20:01', NULL, NULL);
   
INSERT INTO "jobs"
   VALUES (3, 'ASSEMBLY', 1, 0, 12, 1, '2017-10-11 08:15:01', '2017-10-11 21:12:45', '2017-10-11 15:00:59');
   
INSERT INTO "jobs"
   VALUES (4, 'CUTTING', 0, 0, 13, 2, '2017-10-11 08:30:02', NULL, '2017-10-11 14:00:04');
   
INSERT INTO "jobs"
   VALUES (5, 'CUTTING', 0, 1, 2, 5, '2017-10-11 10:45:01', NULL, '2017-10-11 12:40:59');
   
INSERT INTO "project"
   VALUES (1, 'MACHINE 00230', 23, 0, '2017-10-11 08:15:01', NULL);
   
INSERT INTO "project"
   VALUES (2, 'MACHINE 00231', 40, 0, '2017-09-12 08:15:01', NULL);
   
INSERT INTO "project"
   VALUES (3, 'MACHINE 00232', 12, 0, '2017-05-21 08:55:01', NULL);

INSERT INTO "project"
   VALUES (4, 'MACHINE 00233', 13, 0, '2017-10-01 08:11:01', NULL);
   
INSERT INTO "project"
   VALUES (5, 'MACHINE 00234', 202, 1, '2016-09-10 08:00:01', '2017-05-12 23:15:01');
   
INSERT INTO "workers"
   VALUES (1, 'Kasperi', 'Markus', 0, 001);
   
INSERT INTO "workers"
   VALUES (2, 'Junni', 'Saku', 0, 002);

INSERT INTO "workers"
   VALUES (3, 'Jankko', 'Kalervo', 0, 003);
   
INSERT INTO "workers"
   VALUES (4, 'Pöllänen', 'Pertti', 0, 004);
   
INSERT INTO "workers"
   VALUES (5, 'Åkerlund', 'Matias', 0, 005);
         
INSERT INTO "projectjob"
   VALUES (1, 1, 1);
   
INSERT INTO "projectjob"
   VALUES (2, 1, 2);
   
INSERT INTO "projectjob"
   VALUES (3, 1, 3);
   
INSERT INTO "projectjob"
   VALUES (4, 2, 4);
   
INSERT INTO "projectjob"
   VALUES (5, 3, 5);
   
INSERT INTO "workerjob"
   VALUES (1, 3);
   
INSERT INTO "workerjob"
   VALUES (1, 2);
   
INSERT INTO "workerjob"
   VALUES (2, 3);
   
INSERT INTO "workerjob"
   VALUES (3, 4);
   
INSERT INTO "workerjob"
   VALUES (4, 5); 