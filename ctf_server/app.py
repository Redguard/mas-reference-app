from flask import Flask

app = Flask(__name__)

@app.route('/hello', methods=['GET'])
def hello():
    return "Hello, World!"

@app.route('/', defaults={'path': ''})  # Catch-all for other routes
@app.route('/<path:path>')
def catch_all(path):
    return ""

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8008, debug=True)  #debug=True is useful during development
