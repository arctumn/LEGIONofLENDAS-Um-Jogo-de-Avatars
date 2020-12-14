import socket
import DataBase as Db
import GameLogic as Game
import GameShop as Shop
IP = "192.168.2.94"
PORT = 3003

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((IP, PORT))
s.listen(5)


def parse_input(user_address, client_socket, pre_parsed_message):

    # input:
    # ssid
    # operation
    # operation context
    try:
        string_list = pre_parsed_message.split('\n')
        session_id, operation, operation_context = int(string_list[0]), string_list[1], string_list[2]
    except Exception:
        client_socket.send(f"O {user_address} enviou uma mensagem invalida!\n".encode("utf-8"))
        client_socket.close()
        return False

    if session_id == 0:
        Value, newid = Db.create_member_user(operation_context)
        if Value :
            client_socket.send(f"OK New member added with id: {newid}".encode("utf-8"))
        else:
            client_socket.send(f"Error Adding New Row".encode("utf-8"))

    elif session_id != -1 and not Db.check(session_id):
        client_socket.send("INVALID SESSION ID".encode("utf-8"))
    else:
        # operações na DB
        if operation == "DB":
            error, output = Db.run_query(session_id, operation_context)
            if error:
                print("ERROR + DB: " + error)
                client_socket.send(str(error).encode("utf-8"))
            else:
                print("OK + DB")
                client_socket.send(output.encode("utf-8"))
        # operações no jogo
        elif operation == "GAME":
            Game.find_enemy(session_id,operation_context)
            print("OK + GAME")
        # operações na shop
        elif operation == "SHOP":
            error, output = Shop.parse(session_id,operation_context)
            if error:
                client_socket.send(str(error).encode("utf-8"))
                print("ERROR + SHOP: " + error)
            else:
                client_socket.send(str(output).encode("utf-8"))
                print("OK + SHOP")

        else:
            client_socket.send("NO VALID OPERATION".encode("utf-8"))


while True:
    client, address = s.accept()
    print(f"Welcome: {address}")
    try:
        msg = client.recv(1024).decode("utf-8")
        print(msg)
        if not parse_input(address, client, msg):
            print("Finished with client: " + str(address))
        client.close()
    except Exception as e:
        print(e)
        client.send("ERROR ACCEPTING\n".encode("utf-8"))
        client.close()
