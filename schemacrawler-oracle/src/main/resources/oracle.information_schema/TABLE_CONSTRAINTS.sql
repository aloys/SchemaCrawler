SELECT /*+ PARALLEL(AUTO) */
  NULL AS CONSTRAINT_CATALOG,
  CONSTRAINTS.OWNER AS CONSTRAINT_SCHEMA,
  CONSTRAINTS.CONSTRAINT_NAME,
  NULL AS TABLE_CATALOG,
  CONSTRAINTS.OWNER AS TABLE_SCHEMA,
  CONSTRAINTS.TABLE_NAME,
  CASE CONSTRAINTS.CONSTRAINT_TYPE WHEN 'C' THEN 'CHECK' WHEN 'U' THEN 'UNIQUE' WHEN 'P' THEN 'PRIMARY KEY' WHEN 'R' THEN 'FOREIGN KEY' END 
    AS CONSTRAINT_TYPE,
  CASE WHEN CONSTRAINTS.DEFERRABLE = 'NOT DEFERRABLE' THEN 'N' ELSE 'Y' END 
    AS IS_DEFERRABLE,
  CASE WHEN CONSTRAINTS.DEFERRED = 'IMMEDIATE' THEN 'N' ELSE 'Y' END 
    AS INITIALLY_DEFERRED
FROM
  ${catalogscope}_CONSTRAINTS CONSTRAINTS
WHERE
  CONSTRAINTS.OWNER NOT IN 
    ('ANONYMOUS', 'APEX_PUBLIC_USER', 'APPQOSSYS', 'BI', 'CTXSYS', 'DBSNMP', 'DIP', 
    'EXFSYS', 'FLOWS_30000', 'FLOWS_FILES', 'GSMADMIN_INTERNAL', 'IX', 'LBACSYS', 
    'MDDATA', 'MDSYS', 'MGMT_VIEW', 'OE', 'OLAPSYS', 'ORACLE_OCM', 
    'ORDPLUGINS', 'ORDSYS', 'OUTLN', 'OWBSYS', 'PM', 'SCOTT', 'SH', 
    'SI_INFORMTN_SCHEMA', 'SPATIAL_CSW_ADMIN_USR', 'SPATIAL_WFS_ADMIN_USR', 
    'SYS', 'SYSMAN', 'SYSTEM', 'TSMSYS', 'WKPROXY', 'WKSYS', 'WK_TEST', 
    'WMSYS', 'XDB', 'XS$NULL', 'RDSADMIN')  
  AND NOT REGEXP_LIKE(CONSTRAINTS.OWNER, '^APEX_[0-9]{6}$')
  AND NOT REGEXP_LIKE(CONSTRAINTS.OWNER, '^FLOWS_[0-9]{5,6}$')
  AND REGEXP_LIKE(CONSTRAINTS.OWNER, '${schemas}')
  AND CONSTRAINTS.TABLE_NAME NOT LIKE 'BIN$%'
  AND NOT REGEXP_LIKE(CONSTRAINTS.TABLE_NAME, '^(SYS_IOT|MDOS|MDRS|MDRT|MDOT|MDXT)_.*$')
  AND CONSTRAINT_TYPE IN ('C', 'U', 'P', 'R')
