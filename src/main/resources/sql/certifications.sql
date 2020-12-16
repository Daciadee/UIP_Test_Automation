
-- select * from certification where interpreter_id =
--     (select ID from interpreter where email = 'bwitt@utah.gov');

-- delete from certification where id =
--     (select ID from certification where interpreter_id =
--         (select ID from interpreter where email = 'bwitt@utah.gov'));
--
-- select * from fee;
--
-- select * from payment where interpreter_id = 2545;

-- Get Interpreter's Certification IDs
select * from certification where interpreter_id =
    (select ID from interpreter where email = 'uiptester26@gmail.com');

--Get Certification IDs
select id, NAME from CERTIFICATION_TYPE;

--Find interpreter
select * from interpreter where email = 'uiptester25@yahoo.com';  --2483

--Find credits of that interpreter
select * from ce_credit where interpreter_id = 2609;

--Delete those ce_credit
delete from ce_credit where id in (138);

select * from interpreter where email = 'uiptester27@gmail.com';

SELECT * FROM ce_credit WHERE interpreter_id = (select ID from interpreter where email = 'uiptester23@yahoo.com');

DELETE FROM certification WHERE interpreter_id = 2655 AND certification_type_id = (SELECT * FROM certification_type WHERE name = 'NIC');

select * from ATTACHMENT where INTERPRETER_ID = 2615;

select id from CERTIFICATION_TYPE where NAME = 'NIC';

select id from CERTIFICATION where INTERPRETER_ID = 2615 and CERTIFICATION_TYPE_ID = (select id from CERTIFICATION_TYPE where NAME = 'NIC');

select * from ATTACHMENT where RECORD_FK = (select id from CERTIFICATION where INTERPRETER_ID = 2615 and CERTIFICATION_TYPE_ID = (select id from CERTIFICATION_TYPE where NAME = 'NIC'));

SELECT * FROM certification WHERE interpreter_id = 2615 AND CERTIFICATION_TYPE_ID = 33;

select CEH_REQUESTED, PARTIAL_CREDIT_AMT from CE_WORKSHOP where TITLE = 'My TestWorkshop';

