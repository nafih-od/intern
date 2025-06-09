from flask import *
from database import *

api=Blueprint(__name__,'api')

@api.route('/student_login')
def student_login():
    data={}
    username=request.args['username']
    password=request.args['password']

    qry="select * from login where username='%s' and password='%s'"%(username,password)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"

    return str(data)  


@api.route('/view_teachers')
def view_teachers():
    data={}
    # id=request.args['id']

    qry="select * from teacher inner join subject using(subject_id)"
    res=select(qry)
    print(res,"--"*100)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view_teachers"
    return str(data)  



@api.route('/user_teacher_chat')
def user_teacher_chat():
    data={}
    sender_id=request.args['sender_id']
    receiver_id=request.args['receiver_id']
    details=request.args['details']

    a="insert into chat values(null,'%s','student',(select login_id from teacher where teacher_id='%s'),'teacher','%s',curdate(),curtime())"%(sender_id,receiver_id,details)
    res=insert(a)
    if res:
        data['status']="success"
    else:
        data['status']="failed"
    data['method']="chat"

    return str(data)  



    
@api.route('/teacher_chat_details')
def teacher_chat_details():
    data={}
    sender_id=request.args['sender_id']
    receiver_id=request.args['receiver_id']


    f="SELECT * FROM chat WHERE sender_id='%s' AND receiver_id=(select login_id from teacher where teacher_id='%s') UNION SELECT * FROM chat WHERE sender_id=(select login_id from teacher where teacher_id='%s') AND receiver_id='%s' ORDER BY date , time"%(sender_id,receiver_id,receiver_id,sender_id)
    rg=select(f)
    print(rg,"-"*100)
    if rg:
        data['status']="success"
        data['data']=rg
    else:
        data['status']="failed"
    data['method']="chatdetail"
    return str(data)


@api.route('/complaint')
def complaint():
    data={}
    complaint=request.args['description']
    id=request.args['id']

    qry="insert into complaint values(null,'%s','%s','pending',curdate())"%(id,complaint)
    res=insert(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="complaint"
    return str(data)


@api.route('/view_complaint')
def view_complaint():
    data={}
    id=request.args['id']

    qry="select * from complaint where sender_id='%s'"%(id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view_complaint"
    return str(data)



@api.route('/view_qp')
def view_qp():
    data={}


    qry="select * from previous_question_paper inner join subject using(subject_id)"
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view_qp"
    return str(data)  


@api.route('/view_note')
def view_note():

    data={}


    qry="SELECT * FROM lecture_note INNER JOIN teacher USING(teacher_id) INNER JOIN SUBJECT USING(subject_id) INNER JOIN student USING(teacher_id) GROUP BY(note_id)"
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view_note"
    return str(data)  




@api.route('/view_assessment')
def view_assessment():
    data={}

    qry="select * from assessment inner join teacher using(teacher_id) inner join subject using(subject_id)"
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view"
    return str(data)



@api.route('/attend_assessment')
def attend_assessment():
    data={}

    id=request.args['id']
    qry="select * from question_answer where assessment_id='%s'"%(id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view"
    return str(data)