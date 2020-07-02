<call local="local1" global:="global1">1. level with local attribute local=$[local] and global=$[global]
<call>2. level with no further attributes has local=$[local] global=$[global]</call>
<call global:="NULL">3. level with setting global to "NULL" has no values available: local=$[local] global=$[global]</call>
</call>