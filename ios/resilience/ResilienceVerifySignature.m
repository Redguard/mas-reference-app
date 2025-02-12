#import "ResilienceVerifySignature.h"
#import <React/RCTBridgeModule.h>

@implementation ResilienceVerifySignature

RCT_EXPORT_MODULE(ResilienceVerifySignature);

RCT_EXPORT_METHOD(getPackageSignatures:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

@end


