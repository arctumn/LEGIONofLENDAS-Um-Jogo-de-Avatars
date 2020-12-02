import socket


#IP = "82.155.86.142"
IP = "127.0.0.1"
PORT = 3003


client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((IP, PORT))
client_socket.setblocking(True)


#message = "0\nDb\nINSERT INTO user (id,nome,password,level,xp) VALUES (NEWELE,'arctumn','ADMIN',999,9999999)"
#message = "0\nDB\nSELECT * FROM user WHERE id = 3"
message  = "5\nDB\nSELECT * FROM user;"
if message:
    message = message.encode("utf-8")
    client_socket.send(message)

    msg = client_socket.recv(1024).decode("utf-8")

    if len(msg) < 1:
        print("ERROR Receiving")

    print(msg)
