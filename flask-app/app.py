from flask import Flask
app = Flask(__name__)

@app.route('/')
def hello_geek():
    return '<h1><center>Flaskapp is running...</center></h1>'

@app.route('/greet')
def greet():
    return '<h1><center>Flaskapp is greeting...</center></h1>'

if __name__ == "__main__":
    app.run(debug=True)