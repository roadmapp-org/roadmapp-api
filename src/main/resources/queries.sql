USE binnacle;

SELECT p.owner, p.name, t.name, s.name, l.date, l.log
  FROM projects p 
LEFT JOIN tasks t ON p.id = t.project_id
LEFT JOIN subtasks s ON t.id = s.task_id
LEFT JOIN logs l ON p.id = l.project_id
                AND t.id = l.task_id;
                
SELECT * FROM logs;