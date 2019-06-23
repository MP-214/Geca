from flask import Flask, render_template, request, session
app = Flask(__name__)
from Classifier import MyClassifier
app.secret_key = 'any random string'

from Extract import Project
@app.route('/')
def student():
    return render_template('leaf.html')
@app.route('/result', methods=['POST', 'GET'])
def result():
    if request.method == 'POST':
        a = request.form['marks']
        b = request.form['caste']
        # c = request.form['caste']
        # d = request.form['petal-width']
        # inputlist=[a,b,c,d]
        # inputlist = [a, b]
        ob = Project()
        #a = int(a)
        #b = str(b)
        print(a)
        print(b)
        result1 = ob.getlistbymarksandcaste(a, b)
        # ob.predict(list1=inputlist)
        session['result'] = result1
        # result = request.form
        return render_template("prediction.html", result=result)

if __name__ == '__main__':
    app.run(debug=True)