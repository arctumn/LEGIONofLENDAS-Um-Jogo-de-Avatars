import socket
import DataBase as Db
import GameLogic as Game
import GameShop as Shop
import json
from types import SimpleNamespace
IP = ""
PORT = 3003

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((IP, PORT))
s.listen(5)

while True:
    client, address = s.accept()
    print(f"Welcome: {address}")
    try:
        payload = client.recv(1024).decode("utf-8")
        data = json.loads(payload,object_hook=lambda d: SimpleNamespace(**d))
        print(data)
        #if not parse_input(address, client, msg):
        #        print("Finished with client: " + str(address))
        client.close()
    except Exception as e:
        print(e)
        client.send("ERROR ACCEPTING\n".encode("utf-8"))
        client.close()