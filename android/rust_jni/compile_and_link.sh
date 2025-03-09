#!/bin/bash
set -e 

# Directories
JNI_DIR="../app/src/main/jniLibs"

# Might require:
#	`rustup target add aarch64-linux-android armv7-linux-androideabi i686-linux-android x86_64-linux-android`
#	`cargo install cargo-ndk`
#	an appropriately-set ANDROID_NDK_ROOT
cargo ndk --target aarch64-linux-android --platform 21 -- build --release
cargo ndk --target armv7-linux-androideabi --platform 21 -- build --release
cargo ndk --target i686-linux-android --platform 21 -- build --release
cargo ndk --target x86_64-linux-android --platform 21 -- build --release

# Copy the .so files to jniLibs folder
mkdir -p "$JNI_DIR/"{armeabi-v7a,arm64-v8a,x86,x86_64}

cp -v "target/aarch64-linux-android/release/librust_jni.so" "$JNI_DIR/arm64-v8a/"
cp -v "target/armv7-linux-androideabi/release/librust_jni.so" "$JNI_DIR/armeabi-v7a/"
cp -v "target/i686-linux-android/release/librust_jni.so" "$JNI_DIR/x86/"
cp -v "target/x86_64-linux-android/release/librust_jni.so" "$JNI_DIR/x86_64/"

echo "Build completed and .so files copied successfully!"
