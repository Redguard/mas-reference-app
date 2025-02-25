use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;
use jni::sys::jint;
use std::sync::atomic::{AtomicI32, Ordering};

static SCORE: AtomicI32 = AtomicI32::new(0);

// =================
// external fun JNImangle (part1: String, part2: String): String
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_insomnihack_Welcome_JNImangle<'local>(mut env: JNIEnv<'local>, _class: JClass,
    jKotlin_flag: JString<'local>,
    jReact_flag: JString<'local>
) -> jstring {
    /* Just some dumb shit to throw off basic reverse engineering. This flag is shown when winning, after all... */
    let kotlin_flag: String = env.get_string (&jKotlin_flag).expect ("Cannot get jString").into ();
    let react_flag: String = env.get_string (&jReact_flag).expect ("Cannot get jString").into ();

    let uuid: String = "0FE3F0F0-DFD6-4B1D-92A7-005EC104C403".to_string();

    let s_1: Vec<&str> = kotlin_flag.split('-').collect();
    let s_2: Vec<&str> = react_flag.split('-').collect();
    let s_3: Vec<&str> = uuid.split('-').collect();

    /* A UUID has 5 parts. I'm just assuming that both jKotlin_flag and jReact_flag
    actually have a proper UUID format */
    let output = env.new_string(
        format!("{}-{}-{}-{}-{}", s_1[0], s_2[1], s_3[2], s_1[3], s_2[4])
        ).expect("Couldn't create java string!");

    output.into_raw()
}

// =================
// external fun JNIgenHighScoreFlag (): String
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_insomnihack_JniThingies_JNIgenHighScoreFlag<'local>(_env: JNIEnv<'local>, _class: JClass) -> jstring {

    _env.new_string ("asdf").expect ("Couldn't create java string!").into_raw()
}

// =================
// external fun JNInewMatch ()
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_insomnihack_JniThingies_JNInewMatch<'local>(_env: JNIEnv<'local>, _class: JClass) {

    SCORE.fetch_add(1, Ordering::Relaxed);
}

// =================
// external fun JNIadd (amount: Int)
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_insomnihack_JniThingies_JNIadd<'local>(_env: JNIEnv<'local>, _class: JClass, amount: jint) {

    SCORE.fetch_add(amount, Ordering::Relaxed);
}

// =================
// external fun JNIgetScore (): Int
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_insomnihack_JniThingies_JNIgetScore<'local>(_env: JNIEnv<'local>, _class: JClass) -> jint {

    SCORE.load (Ordering::Relaxed)
}

// =================
// external fun JNIresetScore ()
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_insomnihack_JniThingies_JNIresetScore<'local>(_env: JNIEnv<'local>, _class: JClass) {

    SCORE.store (0, Ordering::Relaxed);
}
