
// StorageLog.m
#import "StorageSQLite.h"

@implementation StorageSQLite

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE(StorageSQLite);

RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(createSQLiteDB)
{
  NSLog(@"HELLO LOG");
  return @"Bla";
}

@end
