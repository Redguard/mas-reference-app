#import "CryptoHardcodedSecret.h"
#import <React/RCTBridgeModule.h>

@implementation CryptoHardcodedSecret

RCT_EXPORT_MODULE(CryptoHardcodedSecret);

RCT_EXPORT_METHOD(privateLocalKeys:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(privateEmbeddedKeys:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

RCT_EXPORT_METHOD(hardcodedSymmetricKeys:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

@end


