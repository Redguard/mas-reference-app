from flask import Flask, request, Blueprint, session
from uuid import uuid4
import base64
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.backends import default_backend
from cryptography.exceptions import InvalidSignature

api_v1 = Blueprint("api", __name__, url_prefix = "/1olm72dnrx/api/v1")

# Shitty session handling (don't need more than this)
sessions = [ "66FB159E-8477-4AB3-9029-173725448749" ]

@api_v1.route("/authorise", methods = ["POST"])
def authorise():

    # SHA-256 ("458C0DC0-AA89-4B6D-AF74-564981068AD8"), which is Flag1
    hashed_apikey = "886df6dc8059011d788a34f10802de7a033c431095fc9108d389b759bc16c320"
    received_apikey = request.headers.get ("API-KEY")

    # Not a proper hash comparison, but we don't really care for this scenario
    if not received_apikey == hashed_apikey:
        return  ":(", 403

    data = request.get_json ()

    if not data:
        return "🥸", 403

    # Expected body:
    # {
    #   "k": "<base64-public key>"
    # }
    if not "k" in data:
        return "😤", 403

    pub_key_b64 = data ["k"]

    try:
        der = base64.b64decode (pub_key_b64)
        public_key = serialization.load_der_public_key(
                der,
                backend = default_backend()
            )

        # The App expects RSA/ECB/PKCS1Padding
        encrypted_flag = public_key.encrypt (
                b"66FB159E-8477-4AB3-9029-173725448749", # The flag that will be printed in the logs
                padding.PKCS1v15 ()
            )

        encrypted_flag_b64 = base64.b64encode (encrypted_flag).decode ('utf-8')

    except Exception as e:
        print (e, flush = True)
        return "🤡", 403

    sess_id = encrypted_flag_b64
    #sessions.append (sess_id)

    return encrypted_flag_b64


@api_v1.route("/feedback", methods = ["POST"])
def feedback():

    data = request.get_json ()

    if not data:
        return "🥸", 403

    # Expected body:
    # {
    #   "name": "...",
    #   "feedback": "..."
    #   "sess_id": "..."
    # }
    # We ignore the name and feedback for now, though (maybe it can be used in the future for some flags, or smth?)
    if not "sess_id" in data:
        return "😤", 403

    sess_id = data ["sess_id"]

    if not sess_id in sessions:
        return "🫥", 403

    #sessions.remove (sess_id)

    return "Thank you for the useful feedback!"

app = Flask(__name__)
app.register_blueprint(api_v1)

@app.route('/', defaults={'path': ''}, methods=['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'])
@app.route('/<path:path>', methods=['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'])
def catch_all(path):
    return "😶‍🌫️", 404

