from flask import Flask, render_template, request, session
app = Flask(__name__)
#from Classifier import MyClassifier
app.secret_key = 'any random string'
from Extract import Project
from Extract1 import Project1
@app.route('/')
def leaf():
    return render_template('index.html')
@app.route('/predictorhtml/', methods=['POST', 'GET'])
def predictorhtml():
    return render_template('index.html')

#request.form.get('email')
@app.route('/result1', methods=['POST', 'GET'])
def result1():
    if request.method == 'GET':
        a = request.args.get('marks')
        b = request.args.get('caste')
        c = request.args.get('branch')
        ob = Project()
        a = int(a)
        b = str(b)
        c = str(c)
        ch = " "
        b = b.lstrip(ch)
        b = b.upper()
        c = c.lstrip(ch)
        c = c.capitalize()
        print(a)
        print(b)
        print(c)
        if c == 'No' or c =='no':
            result1 = ob.getlistbymarksandcaste(a, b)
        else:
            result1 = ob.getlistbymarkscasteandbranch(a, b, c)
        session['result'] = result1
        print(result1)
        return result1
        #return render_template("index1.html", result=result1)
    else:
        a = request.form.get('marks')
        b = request.form.get('caste')
        c = request.form.get('branch')
        ob = Project()
        a = int(a)
        b = str(b)
        c = str(c)
        ch = " "
        b = b.lstrip(ch)
        c = c.lstrip(ch)
        b = b.upper()
        c = c.capitalize()
        print(a)
        print(b)
        print(c)
        if c == 'No' or c =='no':
            result1 = ob.getlistbymarksandcaste(a, b)
        else:
            result1 = ob.getlistbymarkscasteandbranch(a, b, c)
        session['result'] = result1
        print(result1)
        return result1
        #return render_template("index1.html", result=result1)

@app.route('/result2', methods=['POST', 'GET'])
def result2():
    if request.method == 'POST':
        a = request.form.get('marks')
        b = request.form.get('caste')
        c = request.form.get('branch')
        print('Marks = ' + a)
        print('Caste = ' + b)
        print('Branch = ' + c)
        a = int(a)
        b = str(b)
        c = str(c)
        ch = " "
        b = b.lstrip(ch)
        c = c.lstrip(ch)
        c = c.capitalize()
        print(a)
        print(b)
        print(c)
        ob = Project1()
        if c == 'No' or c =='no':
            result1 = ob.getlistbymarksandcaste(a, b)
        else:
            result1 = ob.getlistbymarkscasteandbranch(a, b, c)
        #session['result'] = result1
        #print(result1)
        #return result1
        #list1 = ob.getprice()
        mylist = []
        print('app.py')
        for index, row in result1.iterrows():
            # print(index,row[0],row[1])
            try:
                print('Inside for loop')
                mylist.append([index, row[0], row[1], row[2], row[3], row[4], row[5], row[6]])
            except:
                pass
        print('outside for loop')

        # for index, row in result1.iterrows():
        #     # print(index,row[0],row[1])
        #     mylist.append([index, row[0], row[1]])
        # # return list
        # print("Nested List....")
        # z = []
        # for x in mylist:
        #     z = z.append(x)
        # return render_template('index.html',result=list1)
        return render_template('index1.html', data=mylist)
        #return render_template("index1.html", result=result1)

if __name__ == '__main__':
    app.run(debug=True)