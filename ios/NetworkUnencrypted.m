
// StorageLog.m
#import "StorageLog.h"

@implementation StorageLog

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE(StorageLog);

RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(writeSensitiveData)
{
  NSLog(@"HELLO LOG");
  return @"Bla";
}

@end
