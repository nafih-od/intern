from flask import *
from database import *



admin=Blueprint('admin',__name__)


@admin.route('/admin_home')
def admin_home():
    return render_template("admin_home.html")
    


@admin.route('/admin_manage_subject', methods=['POST','GET'])
def admin_manage_subject():
    data={}
    x="select * from subject "
    y=select(x)
    if y:
        data['view']=y 


    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from subject where subject_id='%s'"%(id)
        delete(a)
        return '<script>alert("Subject deleted successfully");window.location="/admin_manage_subject"</script>'
    
    if action=='update':
        z="select * from subject where subject_id='%s'"%(id)
        res=select(z)
        data['up']=res


    if 'update' in request.form:
        subject = request.form['subject']
        a="update subject set subject='%s' where subject_id='%s'"%(subject,id)
        update(a)
        return '<script>alert(" Subject Updated successfully");window.location="/admin_manage_subject"</script>'


    if 'submit' in request.form:
        subject = request.form['subject']
        qry = "select * from subject where subject='%s'"%(subject)
        res=select(qry)
        if res:
            return '<script>alert("subject already exists. Please choose another one.");window.history.back();</script>'
        else:
            qry2="insert into subject values(null,'%s')"%(subject)
            insert(qry2) 
            return ("<script>alert('Subject Added Successfully');window.location='/admin_manage_subject'</script>")

    return render_template("admin_manage_subject.html",data=data)


@admin.route('/admin_manage_course', methods=['POST','GET'])
def admin_manage_course():
    data={}
    x="select * from course "
    y=select(x)
    if y:
        data['view']=y 


    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from course where course_id='%s'"%(id)
        delete(a)
        return '<script>alert("Course deleted successfully");window.location="/admin_manage_course"</script>'
    
    if action=='update':
        z="select * from course where course_id='%s'"%(id)
        res=select(z)
        data['up']=res


    if 'update' in request.form:
        course = request.form['course']
        a="update course set course='%s' where course_id='%s'"%(course,id)
        update(a)
        return '<script>alert(" Course Updated successfully");window.location="/admin_manage_course"</script>'


    if 'submit' in request.form:
        course = request.form['course']
        qry = "select * from course where course='%s'"%(course)
        res=select(qry)
        if res:
            return '<script>alert("Course already exists. Please choose another one.");window.history.back();</script>'
        else:
            qry2="insert into course values(null,'%s')"%(course)
            insert(qry2) 
            return ("<script>alert('course Added Successfully');window.location='/admin_manage_course'</script>")

    return render_template("admin_manage_course.html",data=data)



@admin.route('/admin_manage_teacher', methods=['POST','GET'])
def admin_manage_teacher():
    data={}
    x="select * from subject "
    y=select(x)
    if y:
        data['view']=y 


    data1={}
    b="select * from teacher inner join subject using(subject_id)"
    c=select(b)
    if c:
        data1['view_d']=c    


    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from teacher where login_id='%s'"%(id)
        delete(a)
        b="delete from login where login_id='%s'"%(id)
        delete(b)
        return '<script>alert("Teacher deleted successfully");window.location="/admin_manage_teacher"</script>'
    
    if action=='update':
        z="select * from teacher where login_id='%s'"%(id)
        res=select(z)
        data['up']=res


    if 'update' in request.form:
        subject_id = request.form['subject_id']
        name = request.form['name']
        place = request.form['place']
        phone = request.form['phone']
        email = request.form['email']
        a="update teacher set subject_id='%s',name='%s',place='%s',phone='%s',email='%s' where login_id='%s'"%(subject_id,name,place,phone,email,id)
        update(a)
        return '<script>alert(" Teacher Updated successfully");window.location="/admin_manage_teacher"</script>'


    if 'submit' in request.form:
        subject_id = request.form['subject_id']
        name = request.form['name']
        place = request.form['place']
        phone = request.form['phone']
        email = request.form['email']
        username = request.form['username']
        password = request.form['password']
    
        qry1="insert into login values(null,'%s','%s','teacher')"%(username,password)
        res1=insert(qry1)

        qry2="insert into teacher values(null,'%s','%s','%s','%s','%s','%s')"%(res1,subject_id,name,place,phone,email)
        insert(qry2) 
        return ("<script>alert('Teacher Added Successfully');window.location='/admin_manage_teacher'</script>")

    return render_template("admin_manage_teacher.html",data=data,data1=data1)



@admin.route('/admin_manage_hospital', methods=['POST','GET'])
def admin_manage_hospital():
    data={}
    x="select * from hospital "
    y=select(x)
    if y:
        data['view']=y 


    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    
    else:
        action=None

    if action=='delete':
        a="delete from hospital where login_id='%s'"%(id)
        delete(a)
        b="delete from login where login_id='%s'"%(id)
        delete(b)
        return '<script>alert("Hospital deleted successfully");window.location="/admin_manage_hospital"</script>'
    
    if action=='update':
        z="select * from hospital where login_id='%s'"%(id)
        res=select(z)
        data['up']=res


    if 'update' in request.form:
        hname = request.form['hname']
        type = request.form['type']
        latitude = request.form['latitude']
        longitude = request.form['longitude']
        phone = request.form['phone']
        email = request.form['email']
        a="update hospital set hname='%s', type='%s', latitude='%s',longitude='%s', phone='%s', email='%s' where login_id='%s'"%(hname,type,latitude,longitude,phone,email,id)
        update(a)
        return '<script>alert(" Hospital Updated successfully");window.location="/admin_manage_hospital"</script>'


    if 'submit' in request.form:
        hname = request.form['hname']
        type = request.form['type']
        latitude = request.form['latitude']
        longitude = request.form['longitude']
        phone = request.form['phone']
        email = request.form['email']
        username = request.form['username']
        password = request.form['password']

        qry = "select * from login where username='%s'"%(username)
        res=select(qry)
        if res:
            return '<script>alert("Username already exists. Please choose another one.");window.history.back();</script>'
        else:
            qry1 = "insert into login values(null,'%s','%s','hospital')"%(username,password)
            res1 = insert(qry1)

            qry2="insert into hospital values(null,'%s','%s','%s','%s','%s','%s','%s')"%(res1,hname,type,latitude,longitude,phone,email)
            insert(qry2) 
            return ("<script>alert('Hospital Added Successfully');window.location='/admin_manage_hospital'</script>")

    return render_template("admin_manage_hospital.html", data=data)


@admin.route('/admin_view_complaints', methods=['POST','GET'])
def admin_view_complaints():
    data={}
    x="select * from complaint where reply='pending'"
    y=select(x)
    if y:
        data['view']=y 

    if 'reply' in request.form:
        complaint_id = request.form['complaint_id']
        reply = request.form['reply']
        qry = "update complaint set reply='%s' where complaint_id='%s'" % (reply, complaint_id)
        update(qry)
        return '<script>alert("Reply sent successfully"); window.location="/admin_view_complaints"</script>'

    return render_template("admin_view_complaints.html",data=data)

