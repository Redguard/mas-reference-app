#[macro_use]
extern crate lazy_static;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::{jstring, jint};
use rand::{Rng, SeedableRng};
use rand::rngs::StdRng;
use std::collections::HashMap;
use std::sync::Mutex;
use sha1::{Sha1, Digest};
use uuid::Builder;
use std::ffi::CString;
use pbkdf2::{pbkdf2_hmac};
use sha2::Sha256;
use uuid::Uuid;


// Define the structure to hold your library's state
pub struct GameState {
    score: i32,
    rng: StdRng,
    metadata: HashMap<String, String>,
}

lazy_static! {
    static ref GAME_STATE: Mutex<Option<GameState>> = Mutex::new(None);
}

// --- Initialization and Destruction ---

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgameInit(_env: JNIEnv, _class: JClass) {

    let game_state = GameState {
        score: 0,
        rng: StdRng::seed_from_u64(1234), // Static seed -> maybe to be used for another flag (?)
        metadata: HashMap::new(),
    };

    let mut state_guard = GAME_STATE.lock().unwrap();
    *state_guard = Some(game_state);
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgameDestroy(_env: JNIEnv, _class: JClass) {

    let mut state_guard = GAME_STATE.lock().unwrap();
    *state_guard = None;  // Drop the GameState.
}

// --- Score Management ---

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIincreaseScore(_env: JNIEnv, _class: JClass,
    amount: jint,
) {
    let mut state_guard = GAME_STATE.lock().unwrap();
    if let Some(state) = state_guard.as_mut(){
        state.score += amount as i32;
    }
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIresetScore(_env: JNIEnv, _class: JClass) -> jint {
    let mut state_guard = GAME_STATE.lock().unwrap();
    if let Some(state) = state_guard.as_mut(){
        state.score = 0;
        state.score
    } else {
        -1
    }
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgetScore(_env: JNIEnv, _class: JClass) -> jint {

     let mut state_guard = GAME_STATE.lock().unwrap();
     if let Some(state) = state_guard.as_mut(){
        state.score
    }else{
        -1 // Indicate an error or uninitialized state
    }
}

// --- Metadata Management ---

#[no_mangle]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIsetMetadata(mut env: JNIEnv, _class: JClass,
    key: JString,
    value: JString,
) {
     let mut state_guard = GAME_STATE.lock().unwrap();
     if let Some(state) = state_guard.as_mut(){
        let key_str: String = env.get_string(&key).expect("Couldn't get Java string!").into();
        let value_str: String = env.get_string(&value).expect("Couldn't get Java string!").into();
        state.metadata.insert(key_str, value_str);
    }
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgetMetadata(mut env: JNIEnv, _class: JClass,
    key: JString,
) -> jstring {

    let mut state_guard = GAME_STATE.lock().unwrap();
    if let Some(state) = state_guard.as_mut(){
        let key_str: String = env.get_string(&key).expect("Couldn't get Java string!").into();

        match state.metadata.get(&key_str) {
            Some(value) => {
                let value_jstring = env.new_string(value).expect("Couldn't create Java string!");
                value_jstring.into_raw()
            }
            None => {
                let value_jstring = env.new_string("").expect("Couldn't create Java string!");
                value_jstring.into_raw()
            }
        }
    }
    else{
        let value_jstring = env.new_string("").expect("Couldn't create Java string!");
        value_jstring.into_raw()
    }
}

// --- PRNG Example ---

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgetRandomNumber(_env: JNIEnv, _class: JClass) -> jint {

    let mut state_guard = GAME_STATE.lock().unwrap();
    if let Some(state) = state_guard.as_mut(){

        state.rng.random_range(0..100)
    }
    else{
        -1
    }
}

// --- Flag management ---


#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgenFlagFromMetadata(mut env: JNIEnv,_class: JClass,
    key: JString
) -> jstring {

    let mut state_guard = GAME_STATE.lock().unwrap();
    let flag = if let Some(state) = state_guard.as_mut(){

        let key_str: String = env.get_string(&key).expect("Couldn't get Java string!").into();

        let mut hasher = Sha1::new();
        if let Some(value) = state.metadata.get(&key_str) {
            hasher.update(value);
        }

        let hash = hasher.finalize ();
        let mut sha1_bytes = [0u8; 16]; // uuid::Builder expects a `[u8; 16]`
        sha1_bytes.copy_from_slice(&hash[..16]);

        let uuid = Builder::from_sha1_bytes(sha1_bytes).into_uuid();
        uuid.hyphenated().to_string()

    } else {
        "Oopsie doopsie, I can't generate the flag 😳".to_string()
    };

    let value_jstring = env.new_string(flag).expect("Couldn't create Java string!");
    value_jstring.into_raw()
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgenWololoFlag(mut env: JNIEnv,_class: JClass,
    command: JString,
    token: JString
)  -> jstring {

    let cmd_str: String = env.get_string (&command).expect ("Cannot get jString").into ();
    let token_str: String = env.get_string (&token).expect ("Cannot get jString").into ();

    let mut hasher = Sha1::new();
    
    hasher.update(cmd_str);
    hasher.update(token_str);

    let hash = hasher.finalize ();
    let mut sha1_bytes = [0u8; 16]; // uuid::Builder expects a `[u8; 16]`
    sha1_bytes.copy_from_slice(&hash[..16]);

    let uuid = Builder::from_sha1_bytes(sha1_bytes).into_uuid();

    let value_jstring = env.new_string(
            uuid.hyphenated().to_string()
        ).expect("Couldn't create Java string!");
    value_jstring.into_raw()
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgenSpecialFlag(env: JNIEnv,_class: JClass)  -> jstring {

    let mut state_guard = GAME_STATE.lock().unwrap();
    let flag = if let Some(state) = state_guard.as_mut(){
        /* Just another safeguard, so that players actually discover the exported activity instead of calling `add()` with Frida (that's a different Flag) */
        if state.score == -1234 {

            let mut rng = StdRng::seed_from_u64(state.score as u64);
            let not_so_random_bytes: [u8; 16] = rng.random();

            let uuid = Builder::from_random_bytes(not_so_random_bytes).into_uuid();
            uuid.hyphenated().to_string()
        } else {
            "Come back when you have a better score".to_string()
        }
    } else {
        "Oopsie doopsie, I can't generate the flag 😳".to_string()
    };

    let value_jstring = env.new_string(flag).expect("Couldn't create Java string!");
    value_jstring.into_raw()
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_utils_JniThingies_JNIgenHighScoreFlag(mut env: JNIEnv,_class: JClass,
    key: JString
) -> jstring {

    let mut state_guard = GAME_STATE.lock().unwrap();
    let flag = if let Some(state) = state_guard.as_mut(){
        /* Just another safeguard, so that players actually discover the exported activity instead of calling `add()` with Frida (that's a different Flag) */
        if state.score >= 1000 {

            let key_str: String = env.get_string(&key).expect("Couldn't get Java string!").into();

            let mut hasher = Sha1::new();
            if let Some(value) = state.metadata.get(&key_str) {
                hasher.update(value);
            }

            let hash = hasher.finalize ();
            let mut sha1_bytes = [0u8; 16]; // uuid::Builder expects a `[u8; 16]`
            sha1_bytes.copy_from_slice(&hash[..16]);

            let uuid = Builder::from_sha1_bytes(sha1_bytes).into_uuid();
            uuid.hyphenated().to_string()
        } else {
            "You need at least 1000 points to get the flag! 🙃".to_string()
        }
    } else {
        "Oopsie doopsie, I can't generate the flag 😳".to_string()
    };

    let value_jstring = env.new_string(flag).expect("Couldn't create Java string!");
    value_jstring.into_raw()
}

// =================
// external fun JNImangle (part1: String, part2: String): String
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_Welcome_JNImangle<'local>(mut env: JNIEnv<'local>, _class: JClass,
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

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_org_insomnihack_Welcome_enableExperimentalGuiNative(
    env: JNIEnv,
    _class: JClass,
) -> jstring {
    let password = b"3593f7e8-0b8d-424d-8120-d653d2278b4e";
    let salt = b"5b3e9099-8030-4bd0-b5a8-072c79b6fcf4 ";
    let n = 600_000;
    let mut key1 = [0u8; 16];
    pbkdf2_hmac::<Sha256>(password, salt, n, &mut key1);

    // Modify the key to conform to UUIDv4 format
    key1[6] = (key1[6] & 0x0F) | 0x40; // Set version to 4
    key1[8] = (key1[8] & 0x3F) | 0x80; // Set variant to 10

    let uuid = Uuid::from_bytes(key1);
    let uuidString = format!("Generated UUIDv4: {}", uuid);

    let gui_flag = uuidString;
    let c_string = CString::new(gui_flag.clone()).expect("Failed to create CString");
    let _m1 = CString::new("Just temporary use C-Strings.").expect("CString::new failed");
    let _m2 = CString::new("Don't forget to free the memory after use.").expect("CString::new failed");
    let raw_pointer = c_string.into_raw();
    let raw_pointer_hex = format!("{:p}", raw_pointer);

    let result: Result<String, String> = (|| {
        // Bad coding practice: Parsing an invalid integer string
        let invalid_number = "fortyTwo";
        let _parsed_number: i32 = invalid_number.parse::<i32>().map_err(|e| format!("Parse error: {}", e))?;

        let success_string = String::from("New Experimental GUI initiated, Have fun.");
        Ok(success_string)
    })();
    
    let output = match result {
        Ok(msg) => msg,
        Err(err) => format!("Error occurred: {}, but part of the new experimental GUI has been initiated at: {}", err, raw_pointer_hex),
    };

    let java_string = env.new_string(output).expect("Couldn't create Java string");

    // Second mistake: We dont free the c_string here, the c string remains in memory after as long as the lib is loaded

    java_string.into_raw()
}
