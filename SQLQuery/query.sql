-- create  input table
CREATE TABLE input (
  id text,
  name text
);

INSERT INTO input VALUES (1, 'name1'), (2, 'name2'), (3, 'name3');

-- dummy solution
SELECT n[1], n[2], n[3] FROM (SELECT array_agg(name) n FROM input) t
UNION ALL
SELECT i[1], i[2], i[3] FROM (SELECT array_agg(id) i FROM input) t