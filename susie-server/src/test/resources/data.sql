INSERT INTO ISSUE_STATUS(STATUSID, STATUS_NAME) VALUES (1, 'TO_DO');
INSERT INTO ISSUE_STATUS(STATUSID, STATUS_NAME) VALUES (2, 'IN_PROGRESS');
INSERT INTO ISSUE_STATUS(STATUSID, STATUS_NAME) VALUES (3, 'CODE_REVIEW');
INSERT INTO ISSUE_STATUS(STATUSID, STATUS_NAME) VALUES (4, 'IN_TESTS');
INSERT INTO ISSUE_STATUS(STATUSID, STATUS_NAME) VALUES (5, 'DONE');

INSERT INTO ISSUE_TYPE(ISSUE_TYPEID, TYPE) VALUES (1, 'USER_STORY');
INSERT INTO ISSUE_TYPE(ISSUE_TYPEID, TYPE) VALUES (2, 'BUG');
INSERT INTO ISSUE_TYPE(ISSUE_TYPEID, TYPE) VALUES (3, 'TO_DO');

INSERT INTO ISSUE_PRIORITY(ISSUE_PRIORITYID, PRIORITY) VALUES (1, 'CRITICAL');
INSERT INTO ISSUE_PRIORITY(ISSUE_PRIORITYID, PRIORITY) VALUES (2, 'HIGH');
INSERT INTO ISSUE_PRIORITY(ISSUE_PRIORITYID, PRIORITY) VALUES (3, 'MEDIUM');
INSERT INTO ISSUE_PRIORITY(ISSUE_PRIORITYID, PRIORITY) VALUES (4, 'LOW');
INSERT INTO ISSUE_PRIORITY(ISSUE_PRIORITYID, PRIORITY) VALUES (5, 'TRIVIAL');