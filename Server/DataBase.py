import sqlite3
from sqlite3 import Error

# iniciar a bd
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

# pega numa tabela e mostra o proximo elemento baseado pelo index da tabela
def next_id(table):
    cursor = conn.execute(f"SELECT id FROM {table} ORDER BY id DESC LIMIT 1;")
    for row in cursor:
        print("AQUI "+str(row[0]))
        if row[0]+1:
            print("Value of id: " + str(row[0]+1))
            return int(row[0]+1)
    return False        
            

#create_connection("AndroidDB.db")
conn = sqlite3.connect("AndroidDB.db")

# codigo obsoleto, preferivel fazer isto pelo .sqlite diretamente
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
        return "ERRO",False
    try:
        cid = 0
        print("INSERTING")
        cursor = conn.execute("SELECT id FROM user ORDER BY id DESC LIMIT 1;")
        
        # caso não existam ids, provavelmente já é obsoleto
        if cursor.fetchall() == []:
            conn.execute("INSERT INTO user (id,nome,password,level,xp) VALUES (0, 'ADMIN','ADMIN', 999, '5234201', 0)")
            conn.execute("INSERT INTO status (ID,forca,magia,defesa,defesaMagica,vida,vitorias,derrotas) VALUES (0,999,999,999,999,999,0,0);")
            conn.commit()
            conn.execute("INSERT INTO user (id,nome,password,level,xp) VALUES (1, 'ADMIN2','ADMIN2', 999, '5234201', 0)")
            conn.execute("INSERT INTO status (ID,forca,magia,defesa,defesaMagica,vida,vitorias,derrotas) VALUES (1,999,999,999,999,999,0,0);")
            conn.commit()
        # pega o novo id e prepara a query 
        cid = next_id("user")
        val = "(" + str(cid)
        new_str = test[0] + " " + test[1] + " " + test[2] + " " + test[3] + " " + test[4] +" "
        new_arg = (test[len(test)-1])
        new_arg = val + new_arg[7:]
        # verifica p que vai enviar, provalmente tbm já esta obsoleto
        print("VALORES RECEBIDOS: " + new_arg)
        print("Query: " + new_str + new_arg)
        conn.execute(new_str + new_arg)
        # cria os status do utilizador, o id do user = id do status
        conn.execute(f"INSERT INTO status (ID,forca,magia,defesa,defesaMagica,vida,vitorias,derrotas) VALUES ({cid},1,1,0,0,10,0,0);")
        conn.commit()
        
        return False,str(cid)
    except Error as err:
        return err, False

# primeiro nivel de segurança, a query (tem a falha de permite desde que a pessoa acerte um id, é ignorado s eo id for o do superADMIN)
def check(session_id):

    cursor = conn.execute(f"SELECT * FROM user WHERE id={session_id}")
    for row in cursor:
        if row[1]:
            print("User: " + row[1])

            return True
    print(f"Session id {session_id} does not exist!")
    return False

def loja_insert(operation_context):
    splitted = str(operation_context).split(" ")
    in_values = splitted[-1].strip("\n").split("NEWELE")

    pass
    return True
# executa a query conforme o parametro de entrada !!!!raise security issues!!!!
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

            if checking[3] == "user":
                if not create_member_user(operation_context):
                    return f"User {session_id} already exists", False
                return False, "Success"

            elif checking[3] == "inventario":
                conn.execute(operation_context)
                conn.commit()
                return False, "Success"

            elif checking[3] == "batalhaLOG":
                conn.execute(operation_context)
                conn.commit()
                return False, "Success"

            elif checking[3] == "status":
                conn.execute(operation_context)
                conn.commit()
                return False, "Success"       
            

        elif checking[0] == "CREATE":
            try:

                conn.execute(operation_context)
                conn.commit()

                return False, "Table created"
            except Error:
                return "Table already exists", False
        else:
            return "NO DB OPERATION FOUND", False
    except Exception as e:
        return str(e), False