
// NetworkUnencrypted.m
#import "NetworkUnencrypted.h"

@implementation NetworkUnencrypted

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE(NetworkUnencrypted);

RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(resolveDns)
{
  NSLog(@"HELLO LOG");
  return @"Bla";
}

@end
