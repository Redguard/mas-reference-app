from flask import Flask, request, Blueprint, session
from uuid import uuid4
import base64
import hmac
import hashlib
import logging
import random
from coincurve import PrivateKey
import json
import time
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
from base64 import b64encode

logging.basicConfig(level=logging.INFO)
api_v1 = Blueprint("api", __name__, url_prefix = "/8dj21k01sx/api/v1")

# this directory contains the in memory state
# key: session id generated by client
# value: infos about the match
# example:
# {'<uuid>': {
#     deck: ['paper-plane', 'bomb', ...],
#     open_deck: ['', 'bomb', ...],
#     timestamps: [ts0, ts1, ts2 ...],
#   }
# }

in_memory_state = {}


def detect_timing_attacks(session):
    # trying to detect attacks using timestamps
    pass

def update_client_deck(deck, open_deck):
    if len(deck) != len(open_deck):
        raise Exception("Attempt to cheat detected: Decks must be of the same length")

    new_added_value = 0

    for i in range(len(open_deck)):
        if deck[i] == '':
            deck[i] = open_deck[i]
            new_added_value += 1
        elif deck[i] != open_deck[i]:
            raise Exception(f"Attempt to cheat detected: The card client card does not match the server cards.")
            
    if new_added_value == 0:
        raise Exception(f"Attempt to cheat detected: No newly added values, potentially a cheater is trying to replay requests.")

    if new_added_value > 1:
        raise Exception(f"Attempt to cheat detected: More than one new card opened in one move.")

    return deck

def sign_response(payload):
    with open("/app/signature_keys/pkcs8_private_key.pem", "r") as pem_file:
        pem_data = pem_file.read()
    private_key = PrivateKey.from_pem(pem_data.encode())
    payload_json = json.dumps(payload, separators=(',', ':'))
    signature = private_key.sign(payload_json.encode('utf-8'))
    response = {
            "payload": payload,
            "signature": signature.hex()
        }
    return response 

@api_v1.route("/init", methods = ["POST"])
def init():
    
    logging.info("init called")

    # Expected body:
    # {
    #   "cards": [...]
    # }
    data = request.get_json()
    if not data:
        return "🥸", 403

    logging.info("cards to shuffle:")
    logging.info(data)

    if len(data['cards']) < 10:
        response = sign_response({'status':'error', 'reason':'Attempt to cheat detected: Client uses less than 10 unique cards.'})
        return json.dumps(response), 400

    # generate server deck
    deck = []
    for card in data['cards']:
        deck.append((card))
        deck.append((card))

    random.shuffle(deck)

    # store the deck for future validation
    session = str(uuid4())
    in_memory_state[session] = {
        'timestamps': [time.time()],
        'deck': deck,
        'client_deck': [''] * len(deck),
    }

    payload = {
        'session': session,
        'status': 'success'
    }

    key = b'\xc7\x8e\xa0\x44\x82\x49\xb7\x63\x0e\x2b\x3e\x50\x87\x84\x4a\x93\x86\x01\x31\x1f\xcb\xf5\x10\x7e\xe2\xb3\x55\xfb\xd5\xae\xf6\xd1' 
    iv = b'\x29\x61\x10\xc7\x11\x54\xe5\xea\x40\x89\x9f\x03\xb7\x9f\x9f\x3f'

    deck_bytes = json.dumps(deck).encode('utf-8')
    cipher = AES.new(key, AES.MODE_CBC, iv)
    ct_bytes = cipher.encrypt(pad(deck_bytes, AES.block_size))
    payload['encryptedServerDeck'] = b64encode(ct_bytes).decode('utf-8')

    response = sign_response(payload)

    logging.info("response is: " + str(response))

    return json.dumps(response), 200


@api_v1.route("/validate", methods = ["POST"])
def validate():
    
    logging.info("validate called")

    # Expected body:
    # {
    #   "session": "..."
    #   "openDeck": [...]
    # }
    data = request.get_json()
    if not data:
        return "🥸", 403

    logging.info('player made a new move: ')
    logging.info(data)

    if data['session'] not in in_memory_state:
        response = sign_response({'status':'error', 'reason':'Unknown session'})
        return json.dumps(response), 400

    # test if there are discrepancies between client and server state
    try:
        client_deck = in_memory_state[data['session']]['client_deck']
        in_memory_state[data['session']]['client_deck'] = update_client_deck(client_deck,  data['openDeck'])
        in_memory_state[data['session']]['timestamps'].append(time.time())
        detect_timing_attacks(in_memory_state[data['session']])
    except Exception as e:
        logging.error(e)
        # delete session, otherwise the attacker may use blind attacks to guess each card pair
        del(in_memory_state[data['session']])
        response = sign_response({'status':'error', 'session':data['session'], 'reason':'Something is fishy. Potential cheater detected. Exception was: ' + str(e)})
        return json.dumps(response), 400


    response = sign_response({'status':'success', 'session':data['session'], 'openDeck':in_memory_state[data['session']]['client_deck'], 'reason':'all good'})
    return json.dumps(response), 200


app = Flask(__name__)
app.register_blueprint(api_v1)

@app.route('/', defaults={'path': ''}, methods=['GET', 'POST', 'OPTIONS'])
@app.route('/<path:path>', methods=['GET', 'POST', 'OPTIONS'])
def catch_all(path):
    return "😶‍🌫️", 404

