#import "StorageLog.h"
#import <React/RCTBridgeModule.h>

@implementation StorageLog

RCT_EXPORT_MODULE(StorageLog);

RCT_EXPORT_METHOD(logSensitiveData:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(logPhoneNumber:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(logEmail:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(logFinData:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(logLocation:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

@end


