import DataBase as DB
import functools as fun
def comprar(ssid,itens):
    custo = 0
    ids = []
    error,xp = DB.run_query(ssid, f"SELECT xp FROM user WHERE id = {ssid}")
    if error:
        return "ERRO NA SHOP",False
    
    for item in itens:
        error,info_item = DB.run_query(ssid, f"SELECT ID,price FROM loja WHERE itemName ='{item}'")
        
        if error:
            return "ITEM NAO ENCONTRADO",False
        
        item_id,price = info_item.rstrip("\n").split(" ")
        custo += int(price)
        ids.append(item_id)
    
    if  int(xp)-custo > -1:
        for item_id in ids:
            _,info_item = DB.run_query(ssid, f"SELECT forca,magia,defesa,defesaMagica,vida FROM loja WHERE id = {item_id}")
            _,nome = DB.run_query(ssid, f"SELECT itemName FROM loja WHERE id = {item_id}")
            nome = nome.rstrip("\n")
            info = fun.reduce(lambda x,y:x+ f",{y}",info_item.rstrip("\n").split(" "),f" ({item_id},'{nome}''")
            query = f"INSERT INTO inventario (ID,itemName,forca,magia,defesa,defesaMagica,vida,userID) VALUES{info},{ssid})"
            error,_ = DB.run_query(ssid,query)
            if error:
                return error,False
            return False,"Compra efetuada!"
    else:
        return False, "Sem fundos para comprar esses itens!"

def vender(ssid,itens):
    valor = 0
    error,xp = DB.run_query(ssid, f"SELECT xp FROM user WHERE id = {ssid}")
    if error:
        return "ERRO NA SHOP",False

    for item in itens:
        error,info_item = DB.run_query(ssid, f"SELECT price FROM loja WHERE itemName = '{item}'")
        if error:
            return "ITEM NAO ENCONTRADO",False
        info_price = info_item.rstrip("\n")
        error,info_item_inventario = DB.run_query(ssid, f"SELECT ID FROM inventario WHERE itemName = '{item}'' AND userID = {ssid}")    
        if info_item_inventario != "":
            listItem = info_item_inventario.rstrip("\n").split(" ")
            for _ in listItem:
                valor += int( int(info_price) * 0.4)
            DB.run_query(ssid, f"DELETE FROM inventario WHERE itemName = '{item}'' AND userID = {ssid}")  
        else:
            return False, "ITEM INEXISTENTE NO SEU INVENTARIO"
    valor += int(xp)              
def parse(ssid,operation):
    opsplited = operation.split(" ")
    itens = opsplited[1:len(opsplited)-1]
    if opsplited[0] == "Comprar":
        return comprar(ssid,itens)
    elif opsplited[0] == "Vender":
        return vender(ssid,itens)
    else:              
        return False, "FAILURE"

