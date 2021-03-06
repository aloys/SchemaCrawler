SELECT
  CONSTRAINT_SCHEMA AS CONSTRAINT_CATALOG,
  NULL AS CONSTRAINT_SCHEMA,
  CONSTRAINT_NAME,
  TABLE_SCHEMA AS TABLE_CATALOG,
  NULL AS TABLE_SCHEMA,
  TABLE_NAME,
  CONSTRAINT_TYPE,
  'NO' AS IS_DEFERRABLE,
  'NO' AS INITIALLY_DEFERRED
FROM
  INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE
  CONSTRAINT_TYPE != 'PRIMARY KEY'
  