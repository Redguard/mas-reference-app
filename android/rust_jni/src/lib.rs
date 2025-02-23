use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::jint;

#[no_mangle]
pub unsafe extern "C" fn Java_com_insomnihack_Welcome_test(
    _env: JNIEnv,
    _class: JClass,
    a: jint,
    b: jint
) -> jint {
    a + b
}

