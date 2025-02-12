#import "StorageHardcodedApiKey.h"
#import <React/RCTBridgeModule.h>

@implementation StorageHardcodedApiKey

RCT_EXPORT_MODULE(StorageHardcodedApiKey);

RCT_EXPORT_METHOD(apiKeys:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  resolve(@"[{\"message\": \"iOS code stub.\", \"statusCode\": \"OK\"}]");
}

@end


