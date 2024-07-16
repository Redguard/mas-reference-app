
// ResilienceVerifySignature.m
#import "ResilienceVerifySignature.h"

@implementation ResilienceVerifySignature

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE(StorageLog);

RCT_EXPORT_BLOCKING_SYNCHRONOUS_METHOD(getPackageSignatures)
{
  NSLog(@"HELLO LOG");
  return @"Bla";
}

@end
