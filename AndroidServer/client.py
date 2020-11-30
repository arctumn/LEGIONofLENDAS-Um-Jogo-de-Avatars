import socket


IP = "82.155.86.142"
PORT = 3003


client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((IP, PORT))
client_socket.setblocking(True)


#message = "0\nDb\nINSERT INTO user (id,nome,level,xp) VALUES (3, 'Atumn2', 999, 9999999)"
#message = "1\nDB\nSELECT * FROM user WHERE id = 1"
message  = "1\nDB\nSELECT name FROM sqlite_master WHERE type='table';"
if message:
    message = message.encode("utf-8")
    client_socket.send(message)

    msg = client_socket.recv(1024).decode("utf-8")

    if len(msg) < 1:
        print("ERROR Receiving")

    print("Output: " + msg)
