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


# create_connection("AndroidDB.db")


# conn.execute('''CREATE TABLE user
#     (id INT PRIMARY KEY     NOT NULL,
#     nome            TEXT    NOT NULL,
#     level           INT     NOT NULL,
#     xp              INT     NOT NULL);
# ''')
# conn.execute("INSERT INTO user (id,nome,level,xp) \
#              VALUES (1, 'Atumn', 999, 5234201)")

conn = sqlite3.connect("AndroidDB.db")
def create_member(operation_context):

    try:

        conn.execute(operation_context)
        conn.commit()

        return True
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

            return False, f"Deleted user {session_id}"
        except Error:
            return f"{session_id} does not exists", False

    elif checking[0] == "INSERT":
        if not create_member(operation_context):
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