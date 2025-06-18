import datetime
import uuid
from flask import *
from database import *
import nltk

teacher=Blueprint('teacher',__name__)


@teacher.route('/teacher_home')
def teacher_home():
    return render_template("teacher_home.html")


@teacher.route('/teacher_manage_student',methods=['POST','GET'])
def teacher_manage_student():
    data={}
    x="select * from course "
    y=select(x)
    if y:
        data['view']=y 


    data1={}
    b="select * from student inner join course using(course_id) where teacher_id='%s'"%(session['teacher'])
    c=select(b)
    if c:
        data1['view_d']=c    


    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from student where login_id='%s'"%(id)
        delete(a)
        b="delete from login where login_id='%s'"%(id)
        delete(b)
        return '<script>alert("Student deleted successfully");window.location="/teacher_manage_student"</script>'
    
    if action=='update':
        z="select * from student where login_id='%s'"%(id)
        res=select(z)
        data['up']=res


    if 'update' in request.form:
        course_id = request.form['course_id']
        sem = request.form['sem']
        name = request.form['name']
        place = request.form['place']
        phone = request.form['phone']
        email = request.form['email']
        a="update student set course_id='%s',sem='%s',name='%s',place='%s',phone='%s',email='%s' where login_id='%s'"%(course_id,sem,name,place,phone,email,id)
        update(a)
        return '<script>alert(" Student Updated successfully");window.location="/teacher_manage_student"</script>'


    if 'submit' in request.form:
        course_id = request.form['course_id']
        sem = request.form['sem']
        name = request.form['name']
        place = request.form['place']
        phone = request.form['phone']
        email = request.form['email']
        username = request.form['username']
        password = request.form['password']
    
        qry1="insert into login values(null,'%s','%s','student')"%(username,password)
        res1=insert(qry1)

        qry2="insert into student values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(res1,session['teacher'],course_id,sem,name,place,phone,email)
        insert(qry2) 
        return ("<script>alert('Student Added Successfully');window.location='/teacher_manage_student'</script>")

    return render_template("teacher_manage_student.html",data=data,data1=data1)


@teacher.route('/teacher_view_students')
def teacher_view_students():
    data={}
    b="select * from login inner join student using (login_id) inner join course using(course_id) where teacher_id='%s'"%(session['teacher'])
    c=select(b)
    if c:
        data['view']=c    
    return render_template("teacher_view_students.html",data=data)



@teacher.route('/chat',methods=['post','get'])
def chat():
    data={}
    name=''
    if 'action' in request.args:
        action=request.args['action']
        session['id']=request.args['id']
        name=request.args['name']

    else:
        action=None

    
    
    f="SELECT * FROM chat WHERE sender_id='%s' AND receiver_id='%s' UNION SELECT * FROM chat WHERE sender_id='%s' AND receiver_id='%s' ORDER BY date , time"%(session['id'],session['log'],session['log'],session['id'])
    rg=select(f)
    print(rg)
    data['rg']=rg

    if 'submit' in request.form:
        chat=request.form['chat']
        print(chat,"000000000000000000000000000000000000000000000000")
        a="insert into chat values(null,'%s','teacher','%s','student','%s',curdate(),curtime())"%(session['log'],session['id'],chat)
        insert(a)
        return redirect(url_for('teacher.chat'))
    return render_template("teacherchat.html",data=data,name=name)


#*********************************************************************************************************************************************************************



@teacher.route('/teacher_send_complaint',methods=['POST','GET'])
def teacher_send_complaint():
    if 'submit' in request.form:
        complaint=request.form['complaint']
        qry = "insert into complaint values(null,'%s','%s','pending',curdate())"%(session['teacher'],complaint)
        insert(qry)
        return '<script>alert("Complaint sent successfully");window.location="/teacher_send_complaint"</script>'

    data={}
    qry1 = "select * from complaint where sender_id='%s'"%(session['teacher'])   
    res=select(qry1) 
    if res:
        data['view']=res

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from complaint where complaint_id='%s'"%(id)
        delete(a)
        return '<script>alert("Complaint deleted successfully");window.location="/teacher_send_complaint"</script>'
        
    return render_template("teacher_send_complaint.html",data=data)



@teacher.route('/teacher_upload_qp',methods=['POST','GET'])
def teacher_upload_qp():
    data1={}
    qry2="select * from teacher inner join subject using(subject_id) where teacher_id='%s'"%(session['teacher'])
    res1=select(qry2)
    if res1:
        data1['view']=res1
    if 'submit' in request.form:
        subject=request.form['subject_id']
        year=request.form['year']
        file=request.files['file']
        path='static/'+str(uuid.uuid4())+file.filename
        file.save(path)
        qry = "insert into previous_question_paper values(null,'%s','%s','%s',curdate())"%(subject,year,path)
        insert(qry)
        return '<script>alert("Added");window.location="/teacher_upload_qp"</script>'

    data={}
    qry1 = "select * from previous_question_paper"  
    res=select(qry1) 
    if res:
        data['view']=res

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from previous_question_paper where qp_id='%s'"%(id)
        delete(a)
        return '<script>alert("Question paper deleted successfully");window.location="/teacher_upload_qp"</script>'
        

    return render_template("teacher_upload_qp.html",data=data,data1=data1)    




# import uuid
# import os
# # import moviepy.editor as mp
# import moviepy.editor as mp

# import speech_recognition as sr
# from sumy.parsers.plaintext import PlaintextParser
# from sumy.nlp.tokenizers import Tokenizer
# from sumy.summarizers.lsa import LsaSummarizer

# @teacher.route('/teacher_upload_note',methods=['POST','GET'])
# def teacher_upload_note():

#     data1={}
#     b="select * from lecture_note inner join teacher using(teacher_id) inner join subject using(subject_id)"
#     c=select(b)
#     if c:
#         data1['view_d']=c    


#     if 'action' in request.args:
#         action=request.args['action']
#         id=request.args['id']
    
#     else:
#         action=None

#     if action=='delete':
#         a="delete from lecture_note where note_id='%s'"%(id)
#         delete(a)
#         return '<script>alert("Note deleted successfully");window.location="/teacher_upload_note"</script>'

#     if 'submit' in request.form:
#         title=request.form['title']
#         file=request.files['file']
#         path='static/lecture_note_video/'+str(uuid.uuid4())+file.filename
#         file.save(path)



#         audio_path = path.replace('.mp4', '.wav')  # Change extension
#         clip = mp.VideoFileClip(path)
#         clip.audio.write_audiofile(audio_path)

#         # Extract text from audio
#         recognizer = sr.Recognizer()
#         with sr.AudioFile(audio_path) as source:
#             audio_data = recognizer.record(source)
#             try:
#                 extracted_text = recognizer.recognize_google(audio_data)
#             except sr.UnknownValueError:
#                 extracted_text = "Speech could not be recognized."
#             except sr.RequestError:
#                 extracted_text = "Could not request results from Google Speech Recognition service."

#         # Summarize extracted text
#         parser = PlaintextParser.from_string(extracted_text, Tokenizer("english"))
#         summarizer = LsaSummarizer()
#         summary = summarizer(parser.document, 3)  # Summarizing to 3 sentences
#         summarized_text = " ".join(str(sentence) for sentence in summary)
#         print(summarized_text,"==============")
    
#         qry2="insert into lecture_note values(null,'%s','%s','%s','%s',curdate())"%(session['teacher'],title,path,summarized_text)
#         insert(qry2) 
#         return ("<script>alert('Note Added Successfully');window.location='/teacher_upload_note'</script>")

#     return render_template("teacher_upload_note.html",data1=data1)



import uuid
import os
import moviepy.editor as mp
import speech_recognition as sr
from sumy.parsers.plaintext import PlaintextParser
from sumy.nlp.tokenizers import Tokenizer
from sumy.summarizers.lsa import LsaSummarizer
from flask import request, session, render_template

@teacher.route('/teacher_upload_note', methods=['POST', 'GET'])
def teacher_upload_note():
    data1 = {}
    b = "select * from lecture_note inner join teacher using(teacher_id) inner join subject using(subject_id)"
    c = select(b)
    if c:
        data1['view_d'] = c

    if 'action' in request.args:
        action = request.args['action']
        id = request.args['id']
    else:
        action = None

    if action == 'delete':
        a = "delete from lecture_note where note_id='%s'" % (id)
        delete(a)
        return '<script>alert("Note deleted successfully");window.location="/teacher_upload_note"</script>'

    if 'submit' in request.form:
        title = request.form['title']
        file = request.files['file']
        
        # Save file with unique name
        path = 'static/' + str(uuid.uuid4()) + file.filename
        file.save(path)
        
        # Determine file type based on extension
        file_extension = os.path.splitext(file.filename)[1].lower()
        if file_extension in ['.mp4', '.avi', '.mov', '.wmv']:
            file_type = 'video'
            summarized_text = ""
            
            try:
                # Convert video to audio
                audio_path = path.replace(file_extension, '.wav')
                clip = mp.VideoFileClip(path)
                clip.audio.write_audiofile(audio_path)
                
                # Extract text from audio
                recognizer = sr.Recognizer()
                with sr.AudioFile(audio_path) as source:
                    audio_data = recognizer.record(source)
                    try:
                        extracted_text = recognizer.recognize_google(audio_data)
                        
                        # Summarize extracted text
                        parser = PlaintextParser.from_string(extracted_text, Tokenizer("english"))
                        summarizer = LsaSummarizer()
                        summary = summarizer(parser.document, 3)  # Summarizing to 3 sentences
                        summarized_text = " ".join(str(sentence) for sentence in summary)
                    except sr.UnknownValueError:
                        summarized_text = "Speech could not be recognized."
                    except sr.RequestError:
                        summarized_text = "Could not request results from Google Speech Recognition service."
                
                # Clean up audio file
                if os.path.exists(audio_path):
                    os.remove(audio_path)
                    
            except Exception as e:
                summarized_text = "Error processing video"
        elif file_extension == '.pdf':
            file_type = 'pdf'
            summarized_text = ""
        elif file_extension in ['.jpg', '.jpeg', '.png', '.gif', '.bmp']:
            file_type = 'image'
            summarized_text = ""
        else:
            return '<script>alert("Unsupported file type. Please upload video, PDF, or image files.");window.location="/teacher_upload_note"</script>'
        
        # Insert record into database
        qry2 = "insert into lecture_note values(null,'%s','%s','%s','%s',curdate(),'%s')" % (
            session['teacher'], title, path, summarized_text, file_type)
        insert(qry2)
        return "<script>alert('Note Added Successfully');window.location='/teacher_upload_note'</script>"

    return render_template("teacher_upload_note.html", data1=data1)


# *******************************************************************************************************************************
@teacher.route('/teacher_assessment', methods=['POST', 'GET'])
def teacher_assessment():
    data2 = {}
    data = {}
    x = "SELECT * FROM assessment WHERE teacher_id='%s'" % (session['teacher'])
    y = select(x)
    if y:
        data['view'] = y 

    if 'action' in request.args:
        action = request.args['action']
        set_id = request.args['id']
    else:
        action = None

    if action == 'delete':
        # Corrected delete syntax
        a = "DELETE FROM question_answer WHERE assessment_id='%s'" % (set_id)
        delete(a)
        b = "DELETE FROM assessment WHERE assessment_id='%s'" % (set_id)
        delete(b)
        return '<script>alert("Set Deleted Successfully!");window.location="/teacher_assessment"</script>'
    
    if action == 'update':
        z = "SELECT * FROM assessment INNER JOIN question_answer USING(assessment_id) WHERE assessment_id='%s'" % (set_id)
        res = select(z)
        data2['up'] = res

    if 'update' in request.form:
        title = request.form['title']
        description = request.form['desc']
        question_count = len(data2['up'])  # Get the count of questions from the existing data

        # Update assessment set name
        qry_update_set = "UPDATE assessment SET title='%s', description='%s', date=CURDATE() WHERE assessment_id='%s'" % (title, description, set_id)
        update(qry_update_set)

        for i in range(1, question_count + 1):
            # Get the updated question and answer from the form
            question = request.form.get(f'question{i}')
            option1 = request.form.get(f'option1{i}')
            option2 = request.form.get(f'option2{i}')
            option3 = request.form.get(f'option3{i}')
            answer = request.form.get(f'answer{i}')
            score = request.form.get(f'score{i}')

            # Update question
            question_id = data2['up'][i-1]['question_id']
            qry_update_question = "UPDATE question_answer SET question='%s', option_1='%s', option_2='%s', option_3='%s', answer='%s', score='%s' WHERE question_id='%s'" % (question, option1, option2, option3, answer, score, question_id)
            update(qry_update_question)

        return '<script>alert("Assessment updated successfully!");window.location="/teacher_assessment"</script>'

    if 'submit' in request.form:
        title = request.form['title']
        description = request.form['desc']
        question_count = int(request.form['questionCount'])  # Get the count of questions

        qry2 = "INSERT INTO assessment VALUES (NULL, '%s', '%s', '%s', CURDATE())" % (session['teacher'], title, description)
        res2 = insert(qry2)

        for i in range(1, question_count + 1):
            # Get each question and corresponding answer
            question = request.form.get(f'question{i}')
            option1 = request.form.get(f'option1{i}')
            option2 = request.form.get(f'option2{i}')
            option3 = request.form.get(f'option3{i}')
            answer = request.form.get(f'answer{i}')
            score = request.form.get(f'score{i}')

            # Insert question
            qry1 = "INSERT INTO question_answer VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s')" % (res2, question, option1, option2, option3, answer, score)
            res1 = insert(qry1)

        return '<script>alert("Assessment Added successfully!");window.location="/teacher_assessment"</script>'

    return render_template("teacher_assessment.html", data=data, data2=data2)

@teacher.route('/teacher_view_question_answer')
def teacher_view_question_answer():
    id=request.args['id']
    data={}
    b="select * from question_answer where assessment_id='%s'"%(id)
    c=select(b)
    if c:
        data['view']=c    
    return render_template("teacher_view_question_answer.html",data=data)



# *******************************************************************************************************************************************

