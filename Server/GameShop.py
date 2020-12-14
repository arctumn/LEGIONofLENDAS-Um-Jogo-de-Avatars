import DataBase as DB
import functools as fun
def comprar(ssid,itens):
    custo = 0
    ids = []
    # obtem o xp do utilizador
    error,xp = DB.run_query(ssid, f"SELECT xp FROM user WHERE id = {ssid}")
    if error:
        return "ERRO NA SHOP",False
    # obtem os itens
    for item in itens:
        error,info_item = DB.run_query(ssid, f"SELECT ID,price FROM loja WHERE itemName ='{item}'")
        if error:
            return "ITEM NAO ENCONTRADO",False

        # calcula o preço final
        item_id,price = info_item.rstrip("\n").split(" ")
        custo += int(price)
        # prepara para adicionar os items ao utilizador com base no id
        ids.append(item_id)

    #verifica se o balanço é positivo
    bal = int(xp)-custo

    if  bal > -1:
        for item_id in ids:
            # obtem os status
            err,info_item = DB.run_query(ssid, f"SELECT forca,magia,defesa,defesaMagica,vida FROM loja WHERE id = {item_id}")
            if err:
                return err,False
            # obtem o nome
            err,nome = DB.run_query(ssid, f"SELECT itemName FROM loja WHERE id = {item_id}")
            if err:
                return err,False
            err,image = DB.run_query(ssid, f"SELECT image FROM loja WHERE id = {item_id}")
            if err:
                return err,False
            # prepara a query a ser enviada
            nome = nome.rstrip("\n")
            image = image.rstrip("\n")
            next_ele = DB.next_id("inventario")
            if not next_ele:
                return "",False
            info = fun.reduce(lambda x,y:x+ f",{y}",info_item.rstrip("\n").split(" "),f" ({next_ele},{ssid},'{nome}','{image}',{item_id}")
            query = f"INSERT INTO inventario (id,iduser,idarma,itemName,image,forca,magia,defesa,defesaMagica,vida) VALUES{info})"
            error,_ = DB.run_query(ssid,query)
            # verifica se a operação ocorreu com sucesso, não precisamos de saber que foi adicionado, apenas queremos saber se houve algum erro
            if error:
                return error,False

            # Atualiza o novo xp do utilizador
            query = f"UPDATE user SET xp = {bal} WHERE id = {ssid};"
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

    # calcula a punição de xp na venda de itens
    for item in itens:

        error,info_item = DB.run_query(ssid, f"SELECT price FROM loja WHERE itemName = '{item}'")
        if error:
            return "ITEM NAO ENCONTRADO",False

        # Preço do item em questão
        info_price = info_item.rstrip("\n")
        error,info_item_inventario = DB.run_query(ssid, f"SELECT id FROM inventario WHERE itemName = '{item}'' AND iduser = {ssid}")
        if info_item_inventario != "":
            listItem = str(info_item_inventario).rstrip("\n").split(" ")
            for id_item in listItem:
                valor += int( int(info_price) * 0.4)
                DB.run_query(ssid, f"DELETE FROM inventario WHERE id = {id_item} AND itemName = '{item}'' AND iduser = {ssid}")
        else:
            return False, "ITEM INEXISTENTE NO SEU INVENTARIO"

    # Adiciona o xp ao utilizador
    valor += int(xp)
    error,_ = DB.run_query(ssid, f"UPDATE user SET xp = {valor} WHERE id = {ssid}")
    if error:
        return error,False
    return False, "SUCCESS"

# Recebe e organiza o input
def parse(ssid,operation):
    opsplited = str(operation).split(" ")
    itens = opsplited[1:len(opsplited)-1]
    if opsplited[0] == "Comprar":
        return comprar(ssid,itens)
    elif opsplited[0] == "Vender":
        return vender(ssid,itens)
    else:              
        return False, "FAILURE"

