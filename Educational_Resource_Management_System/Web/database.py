import mysql.connector
user="root"
password=""
database="educational_resource_management_system_2"

def select(q):
	con=mysql.connector.connect(user=user,password=password,host="localhost",database=database,port=3307)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	result=cur.fetchall()
	cur.close()
	con.close()
	return result

def insert(q):
	con=mysql.connector.connect(user=user,password=password,host="localhost",database=database,port=3307)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	result=cur.lastrowid
	cur.close()
	con.close()
	return result

def update(q):
	con=mysql.connector.connect(user=user,password=password,host="localhost",database=database,port=3307)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	res=cur.rowcount
	cur.close()
	con.close()
	return res

def delete(q):
	con=mysql.connector.connect(user=user,password=password,host="localhost",database=database,port=3307)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	result=cur.rowcount
	cur.close()
	con.close()
	return result


def edit(q):
	con=mysql.connector.connect(user=user,password=password,host="localhost",database=database,port=3307)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	res=cur.rowcount
	cur.close()
	con.close()
	return res