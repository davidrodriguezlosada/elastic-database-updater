DECLARE @destinationPhysicalMDFName nVARCHAR(500);
DECLARE @destinationPhysicalLDFName nVARCHAR(500);
DECLARE @originPhysicalMDFName nVARCHAR(500);
DECLARE @originPhysicalLDFName nVARCHAR(500);
DECLARE @databaseName nVARCHAR(500);
DECLARE @LDFName nVARCHAR(500);
DECLARE @backupPath nVARCHAR(500);

SET @databaseName = %DATABASE_NAME%;
SET @backupPath = %BACKUP_PATH%;

-- get phisical names of destination database
SELECT @destinationPhysicalMDFName = physical_name FROM sys.master_files AS mf WHERE type = 0 and DB_NAME(database_id) = @databaseName
SELECT @destinationPhysicalLDFName = physical_name FROM sys.master_files AS mf WHERE type = 1 and DB_NAME(database_id) = @databaseName

--get phisical names of origin database
DECLARE @Table TABLE (LogicalName varchar(128),[PhysicalName] varchar(128), [Type] varchar, [FileGroupName] varchar(128), [Size] varchar(128), 
            [MaxSize] varchar(128), [FileId]varchar(128), [CreateLSN]varchar(128), [DropLSN]varchar(128), [UniqueId]varchar(128), [ReadOnlyLSN]varchar(128), [ReadWriteLSN]varchar(128), 
            [BackupSizeInBytes]varchar(128), [SourceBlockSize]varchar(128), [FileGroupId]varchar(128), [LogGroupGUID]varchar(128), [DifferentialBaseLSN]varchar(128), [DifferentialBaseGUID]varchar(128), [IsReadOnly]varchar(128), [IsPresent]varchar(128), [TDEThumbprint]varchar(128)
)
DECLARE @Path varchar(1000)=@backupPath
DECLARE @LogicalNameData varchar(128),@LogicalNameLog varchar(128)
INSERT INTO @table
EXEC('
RESTORE FILELISTONLY 
   FROM DISK=''' +@Path+ '''
   ')

   SET @LogicalNameData=(SELECT LogicalName FROM @Table WHERE Type='D')
   SET @LogicalNameLog=(SELECT LogicalName FROM @Table WHERE Type='L')

SELECT @originPhysicalMDFName = @LogicalNameData, @originPhysicalLDFName = @LogicalNameLog

--Restore database
RESTORE DATABASE @databaseName FROM  DISK = @backupPath
WITH  FILE = 1,
MOVE @originPhysicalMDFName TO @destinationPhysicalMDFName,
MOVE @originPhysicalLDFName TO @destinationPhysicalLDFName,
NOUNLOAD,
REPLACE,
STATS = 5