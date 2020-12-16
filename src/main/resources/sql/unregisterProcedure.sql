DECLARE
  v_email VARCHAR2(255) := '''uiptester23@yahoo.com''';
  v_statement VARCHAR2(255) := 'SELECT id FROM interpreter WHERE email = ' || v_email;
  v_id NUMBER;

BEGIN
  EXECUTE IMMEDIATE v_statement INTO v_id;
  DELETE FROM agreement WHERE interpreter_id = v_id;
  DELETE FROM certification_progress WHERE interpreter_id =  v_id;
  DELETE FROM certification WHERE interpreter_id = v_id;

  DELETE FROM pe_answer WHERE pe_component_assignment_id IN
    (SELECT id FROM pe_component_assignment WHERE pe_comp_RATER_TYPE_ID IN
      (SELECT id FROM pe_comp_rater_type WHERE pe_component_id IN
        (SELECT id FROM pe_component WHERE pe_id IN
          (SELECT id FROM performance_exam WHERE interpreter_id = v_id))));

  DELETE FROM pe_component_assignment WHERE PE_COMP_RATER_TYPE_ID IN
    (SELECT id FROM pe_comp_rater_type WHERE pe_component_id IN
      (SELECT id FROM pe_component WHERE pe_id IN
        (SELECT id FROM performance_exam WHERE interpreter_id = v_id)));

  DELETE FROM pe_comp_rater_type WHERE pe_component_id IN
    (SELECT id FROM pe_component WHERE pe_id IN
      (SELECT id FROM performance_exam WHERE interpreter_id = v_id));

  DELETE FROM pe_component WHERE pe_id IN
    (SELECT id FROM performance_exam WHERE interpreter_id = v_id);

  DELETE FROM performance_exam WHERE interpreter_id = v_id;
  DELETE FROM ke_exam WHERE ke_id IN
    (SELECT id FROM knowledge_exam WHERE interpreter_id = v_id);

  DELETE FROM knowledge_exam WHERE interpreter_id = v_id;
  DELETE FROM payment WHERE interpreter_id = v_id;
  DELETE FROM alerts WHERE interpreter_id = v_id;
  DELETE FROM pe_answer WHERE PE_COMPONENT_ASSIGNMENT_ID IN
    (SELECT id FROM pe_component_assignment WHERE PE_TYPE_RATER_ID IN
      (SELECT id FROM pe_type_rater WHERE INTERPRETER_ID = v_id));

  DELETE FROM pe_component_assignment WHERE PE_TYPE_RATER_ID IN (SELECT id FROM pe_type_rater WHERE INTERPRETER_ID = v_id);
  DELETE FROM pe_type_rater WHERE interpreter_id = v_id;
  DELETE FROM ce_credit WHERE interpreter_id = v_id;
  DELETE FROM ce_approval WHERE interpreter_id = v_id;

  UPDATE exam_schedule
  SET interpreter_id = NULL, exam_id = NULL, held = 0
  WHERE interpreter_id = v_id;

  COMMIT;

END;
