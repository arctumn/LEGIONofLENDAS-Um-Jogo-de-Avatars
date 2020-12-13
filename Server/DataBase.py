import sqlite3
from sqlite3 import Error


def create_connection(db_file):
    conn = None
    try:
        conn = sqlite3.connect(db_file)
        print(sqlite3.version)
    except Error as e:
        print(e)
    finally:
        if conn:
            conn.close()


#create_connection("AndroidDB.db")
conn = sqlite3.connect("AndroidDB.db")

#conn.execute('''CREATE TABLE user
#     (id INT PRIMARY KEY     NOT NULL,
#     nome            TEXT    NOT NULL,
#     password        TEXT    NOT NULL,
#     level           INT     NOT NULL,
#     xp              INT     NOT NULL,
#     image           INT     NOT NULL);
# ''')
#conn.execute("INSERT INTO user (id,nome,password,level,xp,image) VALUES (0, '----','----', 0, 0, 0)")
#conn.execute("INSERT INTO user (id,nome,password,level,xp,image)  VALUES (1, 'ADMIN','ADMIN', 999, 5234201, 0)")
#conn.commit()
#conn.close()

conn = sqlite3.connect("AndroidDB.db")
def create_member_user(operation_context):
    test = str(operation_context).split(' ')
    print(test)
    if test[0] != "INSERT":
        return False
    try:
        cid = 0
        print("INSERTING")
        cursor = conn.execute("SELECT id FROM user ORDER BY id DESC LIMIT 1;")
        if cursor.fetchall() == []:
            conn.execute("INSERT INTO user (id,nome,password,level,xp) VALUES (0, 'ADMIN','ADMIN', 999, 5234201, 0)")
            conn.commit()
            conn.execute("INSERT INTO user (id,nome,password,level,xp) VALUES (1, 'ADMIN2','ADMIN2', 999, 5234201, 0)")
            conn.commit()
        cursor = conn.execute("SELECT id FROM user ORDER BY id DESC LIMIT 1;")
        for row in cursor:
            if row[0]:
                print("Value of id: " + str(row[0]+1))
                cid = row[0]+1
                val = "("+str(int(row[0]) + 1)
                new_str = test[0] + " " + test[1] + " " + test[2] + " " + test[3] + " " + test[4] +" "
                new_arg = (test[len(test)-1])
                new_arg = val + new_arg[7:]
                print("VALORES RECEBIDOS: " + new_arg)
                print("Query: " + new_str + new_arg)    
                conn.execute(new_str + new_arg)
                conn.commit()
        
        return True,str(cid)
    except Error:
        return False


def check(session_id):

    cursor = conn.execute(f"SELECT * FROM user WHERE id={session_id}")
    for row in cursor:
        if row[1]:
            print("User: " + row[1])

            return True
    print(f"Session id {session_id} does not exist!")
    return False


def run_query(session_id, operation_context):
    checking = str(operation_context).split(' ')
    try:
        if session_id == -1:
            cursor = conn.execute(operation_context)
            conn.commit()
            string = ""
            for row in cursor:
                for ele in row:
                    string += str(ele) + " "
                string += "\n"
            return False, string

        if checking[0] == "SELECT":
            cursor = conn.execute(operation_context)
            string = ""
            for row in cursor:
                for ele in row:
                    string += str(ele) + " "
                string += "\n"
            return False, string

        elif checking[0] == "UPDATE":

            conn.execute(operation_context)
            conn.commit()

            return run_query(session_id, f"SELECT * FROM user WHERE id={session_id}")

        elif checking[0] == "DELETE":
            try:

                conn.execute(operation_context)
                conn.commit()

                return False, f"Deleted Successfull"
            except Error:
                return f"Failed to delete", False

        elif checking[0] == "INSERT":
            if not create_member_user(operation_context):
                return f"User {session_id} already exists", False
            return False, "Success"

        elif checking[0] == "CREATE":
            try:

                conn.execute(operation_context)
                conn.commit()

                return False, "Table created"
            except Error:
                return "Table already exists", False
        else:
            return "NO DB OPERATION FOUND"
    except Exception as e:
        return str(e), False