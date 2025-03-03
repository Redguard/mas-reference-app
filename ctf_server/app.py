from flask import Flask, request, Blueprint

api_v1 = Blueprint("api", __name__, url_prefix = "/1olm72dnrx/api/v1")

@api_v1.route("/authorise", methods = ["POST"])
def authorise():

    # SHA-256 ("458C0DC0-AA89-4B6D-AF74-564981068AD8"), which is Flag1
    hashed_apikey = "886df6dc8059011d788a34f10802de7a033c431095fc9108d389b759bc16c320"
    received_apikey = request.headers.get ("API-KEY")

    print (request.json, flush = True)

    # Not a proper hash comparison, but we don't really care for this scenario
    if not received_apikey == hashed_apikey:
        return  ":("

    return "Yay!"


@api_v1.route("/feedback", methods = ["POST"])
def feedback():

    # SHA-256 ("458C0DC0-AA89-4B6D-AF74-564981068AD8")
    hashed_apikey = "886df6dc8059011d788a34f10802de7a033c431095fc9108d389b759bc16c320"
    received_apikey = request.headers.get ("API-KEY")

    # Not a proper hash comparison, but we don't really care for this scenario
    if not received_apikey == hashed_apikey:
        return  ":("


    return "Yay!"

app = Flask(__name__)
app.register_blueprint(api_v1)

@app.route('/', defaults={'path': ''}, methods=['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'])
@app.route('/<path:path>', methods=['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'])
def catch_all(path):
    print("Catch-all!", flush = True)
    return ""

