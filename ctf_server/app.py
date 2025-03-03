from flask import Flask, request, Blueprint, session
from uuid import uuid4
import pprint

api_v1 = Blueprint("api", __name__, url_prefix = "/1olm72dnrx/api/v1")

# Shitty session handling (don't need more than this)
sessions = []

@api_v1.route("/authorise", methods = ["POST"])
def authorise():

    # SHA-256 ("458C0DC0-AA89-4B6D-AF74-564981068AD8"), which is Flag1
    hashed_apikey = "886df6dc8059011d788a34f10802de7a033c431095fc9108d389b759bc16c320"
    received_apikey = request.headers.get ("API-KEY")

    # Not a proper hash comparison, but we don't really care for this scenario
    if not received_apikey == hashed_apikey:
        return  ":(", 403

    sess_id = str (uuid4())
    sessions.append (sess_id)

    return sess_id


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

    sessions.remove (sess_id)

    return "Thank you for the useful feedback!"

app = Flask(__name__)
app.register_blueprint(api_v1)

@app.route('/', defaults={'path': ''}, methods=['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'])
@app.route('/<path:path>', methods=['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'])
def catch_all(path):
    return "😶‍🌫️", 200

