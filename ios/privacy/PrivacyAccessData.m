#import "PrivacyAccessData.h"
#import <React/RCTBridgeModule.h>

@implementation PrivacyAccessData

RCT_EXPORT_MODULE(PrivacyAccessData);

RCT_EXPORT_METHOD(getContacts:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(getCalendarEvent:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(getLocation:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(getSMS:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(sendSMS:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

@end


