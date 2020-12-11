<<<<<<< HEAD
import functools as fun
import random as rand

import DataBase as db


def get_itens(id):  # Vai buscar os items
    err, userID_itens = db.run_query(id,
                                     f"SELECT * FROM inventario WHERE userid = {id};")  # idarma + str + mag + def + defm + hp + iduser
    userID_itens = userID_itens.split("\n")
    return err, userID_itens


def get_stats(id):  # Vai buscar os stats
    err, userID_status = db.run_query(id, f"SELECT * FROM status WHERE userid = {id};")
    userID_status = userID_status.split("\n")
    return err, userID_status


def randomuser(id1, userSTAT):
    query = f"SELECT ID FROM status WHERE \
    forca > {int(userSTAT[0]) - 10} AND forca < {int(userSTAT[0]) + 10} \
    magia > {int(userSTAT[1]) - 10} AND magia < {int(userSTAT[1]) + 10} \
    defesa > {int(userSTAT[2]) - 10} AND defesa < {int(userSTAT[2]) + 10} \
    defesaMagica > {int(userSTAT[3]) - 10} AND defesaMagica < {int(userSTAT[3]) + 10} \
    vida > {int(userSTAT[4]) - 10} AND vida < {int(userSTAT[4]) + 10};"

    _, elements = db.run_query(id1, query)
    if elements == "":
        return "TRYBOT", False
    listofchoosen = elements.split("\n")
    return False, listofchoosen[rand.randint(0, len(listofchoosen) - 1)]
=======
import DataBase as db
import functools as fun
import random as rand
def get_itens(id):
    err,userID_itens = db.run_query(id,f"SELECT * FROM inventario WHERE userid = {id};")
    userID_itens = userID_itens.split("\n")    
    return err, userID_itens
def get_stats(id):
    err,userID_itens = db.run_query(id,f"SELECT * FROM status WHERE userid = {id};")
    userID_itens = userID_itens.split("\n")    
    return err, userID_itens

def randomuser(id1,userSTAT):
    query = f"SELECT ID FROM status WHERE \
    forca > {int(userSTAT[0])-10} AND forca < {int(userSTAT[0])+10} \
    magia > {int(userSTAT[1])-10} AND magia < {int(userSTAT[1])+10} \
    defesa > {int(userSTAT[2])-10} AND defesa < {int(userSTAT[2])+10} \
    defesaMagica > {int(userSTAT[3])-10} AND defesaMagica < {int(userSTAT[3])+10} \
    vida > {int(userSTAT[4])-10} AND vida < {int(userSTAT[4])+10};"

    _,elements = db.run_query(id1,query)
    if elements == "":
        return "TRYBOT",False
    listofchoosen = elements.split("\n")
    return False,listofchoosen[rand.randint(0,len(listofchoosen)-1)]
>>>>>>> bb96567ec4745b7139d930433989204af264aa82


# Precisas englobar isso tudo dentro de uma função bc isso é chamado para diferentes players
# trata de encorporar isso dentro do find_enemy depois é so somares ou usares o user2/itens/stats e user1/itens/stats


<<<<<<< HEAD
def find_enemy(id1, operation_context, enemyID):
    # operation context = PLAYER (um player aleatorio)
    # operation context = BOT (pegas nas infos do primeiro e fazes um rand entre -10 e + 10 de todos os status do player)
    # operation context = ID (Player através do QR code)

    # itens u1
    # err, user1_itens = get_itens(id1)[:-1]

    # Versao de teste:
    user1_itens = TesteItems1.split("\n")[:-1]
    for a in user1_itens:
        print(a)

    err = False
    if err:
        print(err)
        return err, False
        # status u1
    # err, user1_status = get_stats(id1)[:-1]

    # Versao de teste:
    user1_status = TesteUser1.split("\n")[:-1]

    if err:
        print(err)
        return err, False
    user1STAT = ((user1_status[0]).split(" "))[1:]

    s = ""
    for a in user1STAT:
        s = s + str(a) + " "
    print("u1 carregado com os seguintes stats: " + s)

    if operation_context == "Player":
        err, uid = randomuser(id1, user1STAT)
        if err == "TRYBOT":
            find_enemy(id1, "BOT", -1)
        elif err:
            print(err)
            return err, False
            # itens u2
        err, user2_itens = get_itens(uid)
        if err:
            print(err)
            return err, False
            # status u2
=======

def find_enemy(id1,operation_context):
    # operation context = PLAYER (um player aleatorio)
    # operation context = BOT (pegas nas infos do primeiro e fazes um rand entre -10 e + 10 de todos os status do player)
    # operation context = ID (Player através do QR code)
    
        #itens u1
    err,user1_itens = get_itens(id1)
    if err:
        print(err)
        return err, False
        #status u1
    err,user1_status = get_stats(id1)
    if err:
        print(err)
        return err, False

    user1STAT = ((user1_status[0]).split(" "))[1:]

    if operation_context == "Player":
        err,uid = randomuser(id1,user1STAT)
        if err:
            print(err)
            return err, False
            #itens u2
        err,user2_itens = get_itens(uid)
        if err:
            print(err)
            return err, False
            #status u2
>>>>>>> bb96567ec4745b7139d930433989204af264aa82
        err, user2_stats = get_stats(uid)
        if err:
            print(err)
            return err, False
<<<<<<< HEAD

        user2STAT = ((user2_stats[0]).split(" "))[1:]
        s = ""
        for a in user2STAT:
            s = s + str(a) + " "
        print("u2 carregado com os seguintes stats: " + s)
        inicioCombate(user1STAT, user2STAT, user1_itens, user2_itens, id1, uid)


    elif operation_context == "BOT":
        # faz a matematica para os status do bot
        def calcStatus(x, y):
            y = " " + str(rand.randint(int(y) - 10, int(y) + 10))
            return x + y
=======
    
    elif operation_context == "BOT" :
        #faz a matematica para os status do bot
        def calcStatus(x,y):
            y = " " + str(rand.randint(int(y)-10,int(y)+10))
            return x+y
>>>>>>> bb96567ec4745b7139d930433989204af264aa82

        # isto é os itens do user2 because BOT u1itens = u2itens
        user2_itens = user1_itens
        # isto é os id do user2 + status do user 2 separados por espaço
        # example input lista = ["5", "10", "15", "20", "25", "30"] output = "5 0 6 16 22 28"
<<<<<<< HEAD
        user2_stats = fun.reduce(calcStatus, user1STAT[1:], user1STAT[0])

    elif operation_context == "ID":
        # itens u2
        # Versao teste:
        err = False
        user2_itens = TesteItems2.split("\n")[:-1]
        user2_stats = TesteUser2.split("\n")[:-1]
        print(" ID1 E 2: " + str(id1) + " " + str(enemyID))

        # err, user2_itens = get_itens(enemyID)
        if err:
            print(err)
            return err, False
            # status u2
        # err, user2_stats = get_stats(enemyID)
        if err:
            print(err)
            return err, False

        user2STAT = ((user2_stats[0]).split(" "))[1:]
        s = ""
        for a in user2STAT:
            s = s + str(a) + " "
        print("u2 carregado com os seguintes stats: " + s)
        inicioCombate(user1STAT, user2STAT, user1_itens, user2_itens, id1, enemyID)

    else:
        # itens u2
        err, user2_itens = get_itens(operation_context)
        if err:
            print(err)
            return err, False
            # status u2
        err, user2_stats = get_stats(operation_context)
        if err:
            print(err)
            return err, False
    pass
=======
        user2_stats = fun.reduce(calcStatus,user1STAT[1:],user1STAT[0])

    else:
            #itens u2
        err,user2_itens = get_itens(operation_context)
        if err:
            print(err)
            return err, False
            #status u2
        err,user2_stats = get_stats(operation_context)
        if err:
            print(err)
            return err, False
    pass    
>>>>>>> bb96567ec4745b7139d930433989204af264aa82


class Combatente:  # Warriors
    def __init__(self, stren, mag, defn, defm, hp, idu):
        self.stren = stren
        self.mag = mag
        self.defn = defn
        self.defm = defm
        self.hp = hp
        self.idu = idu


class ItemPorUser:  # Items
    def __init__(self, stren, mag, defn, defm, hp, idu):
        self.stren = stren
        self.mag = mag
        self.defn = defn
        self.defm = defm
        self.hp = hp
        self.idu = idu


# Item order: id + força + magia + def + defm + hp + userid
# User: id + str + mag + def + defm + hp

<<<<<<< HEAD
def controloResults(u, d, n):
=======

#
items = ""  # Linha com items do 1o e 2o utilizador
users = ""  # Linha com stats do 1o e 2o utilizador


def controloResults(u, dn, dm):
>>>>>>> bb96567ec4745b7139d930433989204af264aa82
    # Enviar para o utilizador os resultados da ronda de combate:
    # u.hp = vida actual do user atacado
    # d = dano total
    # n = nº de ronda
    return 0


# Calcula o dano de um utilizador
def calculoDano(u, it):
    print("A calcular dano")
    dano = []
    # Criar loop para calcular com várias armas:
<<<<<<< HEAD
    dano.append(u.stren)  # Dano fisico
    dano.append(u.mag)  # Dano mágico
    for x in it:  # Percorre as armas
        dano[0] = dano[0] + x.stren
        dano[1] = dano[1] + x.mag
=======
    dano[0] = u.stren  # Dano fisico
    dano[1] = u.mag  # Dano mágico
    for x in it:  # Percorre as armas
        dano[0] = dano[0] + u.stren * x.stren
        dano[1] = dano[1] + u.mag * x.mag
>>>>>>> bb96567ec4745b7139d930433989204af264aa82
    return dano  # Possibilidade de adicionar % aleatórios de dano bónus/retirado?, exemplo (pseudo-código):
    # (dano[0] * random(0.95, 1.05), dano[1] * random(0.95, 1.05)
    # return dano


def finalDamage(damage, defesaValue):
    if damage == 0:
        damage = rand.randint(0, 10)
    elif 0 < damage <= 10:
        damage = damage + rand.randint(int(damage * -0.2), int(damage * 0.2))
    elif 10 < damage <= 50:
        damage = damage + rand.randint(int(damage * -0.1), int(damage * 0.1))
    elif 50 < damage:
        damage = damage + rand.randint(int(damage * -0.05), int(damage * 0.05))
    balance = defesaValue / damage
    print("damage = " + str(damage) + " balance: " + str(balance))

    fd = -1

    if 0 <= balance <= 0.2:
        fd = damage - (defesaValue - (defesaValue * 0.9))
    elif 0.2 < balance <= 0.5:
        fd = damage - (defesaValue - (defesaValue * 0.7))
    elif 0.5 < balance <= 0.7:
        fd = damage - (defesaValue - (defesaValue * 0.5))
    elif 0.7 < balance <= 0.9:
        fd = damage - (defesaValue - (defesaValue * 0.3))
    elif balance > 0.9:
        fd = damage - (defesaValue - (defesaValue * 0.15))
    return fd


def damageDone(magicDamage, magicDefense, physicDamage, physicDefense):
    magicDamage = finalDamage(magicDamage, magicDefense)
    physicDamage = finalDamage(physicDamage, physicDefense)
    return magicDamage * 0.5 + physicDamage * 0.5


def Combate(u1, u2, it1, it2, pa, n):  # Pa -> Primeiro a Atacar
    print(" A iniciar ronda: " + str(n))
    if (u1.hp <= 0) and (u2.hp <= 0):
        print("Acabar combate, empate")
        return 0
    if u1.hp <= 0:
        print("acabar combate, u2 venceu")
        return 2
    if u2.hp <= 0:
        print("acabar combate, u1 venceu")
        return 1

    print("A iniciar o combate")
    # d1 = calculoDano(u1, it1)
    # d2 = calculoDano(u2, it2)

    if pa == -1:  # Primeira execução, escolhe um para começar aleatóriamente
        print("A escolher primeiro a atacar... ")
        pa = rand.randint(1, 2)
        print("Escolhido o utilizador u" + str(pa))

    if pa == 1:  # User 1 é o primeiro a atacar
        print("A iniciar o ataque de u1...")
        defesa = [0, 0]
        d = calculoDano(u1, it1)
        print("Dano = " + str(d[0]) + ", dano mágico = " + str(d[1]))

        for x in it2:
            defesa[0] = defesa[0] + x.defn
            defesa[0] = defesa[1] + x.defm
        print("Defesa do adversário = " + str(defesa[0]) + ", defesa mágica = " + str(defesa[1]))
        fd = damageDone(d[1], defesa[1], d[0], d[1])

        print("Dano = " + str(fd))
        if fd < 0:
            print("Vida antes do ataque (u1): " + str(u1.hp))
            u1.hp = u1.hp + fd
            print("Vida depois do ataque (u1): " + str(u1.hp))
        elif fd > 0:
            print("Vida antes do ataque (u2): " + str(u2.hp))
            u2.hp = u2.hp - fd
            print("Vida depois do ataque (u2): " + str(u2.hp))
        pa = 2
        controloResults(u2, fd, n)
        # Obs na minha opinião poderíamos mudar a defesa
        # para ser feita com base em %.

    elif pa == 2:  # User 2 é o primeiro a atacar
        print("A iniciar o ataque de u2...")
        defesa = [0, 0]
        d = calculoDano(u2, it2)
        print("Dano = " + str(d[0]) + ", dano mágico = " + str(d[1]))

        for x in it1:
            defesa[0] = defesa[0] + x.defn
            defesa[1] = defesa[1] + x.defm
        print("Defesa do adversário = " + str(defesa[0]) + ", defesa mágica = " + str(defesa[1]))
        fd = damageDone(d[1], defesa[1], d[0], d[1])

        print("Dano = " + str(fd))
        if fd < 0:
            print("Vida antes do ataque (u2): " + str(u2.hp))
            u2.hp = u2.hp + fd
            print("Vida depois do ataque (u2): " + str(u2.hp))
        elif fd > 0:
            print("Vida antes do ataque (u1): " + str(u1.hp))
            u1.hp = u1.hp - fd
            print("Vida depois do ataque (u1): " + str(u1.hp))
        pa = 1
        controloResults(u1, fd, n)

    Combate(u1, u2, it1, it2, pa, n + 1)


# Função Principal do decorrer do combate
def inicioCombate(u1, u2, i1, i2, id1, id2):
    # Fazer query da string users e da string items

    print("Inicio do Combate entre user1 -> " + str(id1) + " e user2 -> " + str(id2))
    # Cria os objectos combatentes
    user1 = Combatente(int(u1[0]), int(u1[1]), int(u1[2]), int(u1[3]), int(u1[4]), int(id1))
    user2 = Combatente(int(u2[0]), int(u2[1]), int(u2[2]), int(u2[3]), int(u2[4]), int(id2))
    # item[x] =  idarma + str + mag + def + defm + hp + iduser
    item1 = []
    item2 = []
    for x in i1:  # Cria os objectos dos items e distribui por utilizador
        print(x)
        b = x.split(" ")
        item1.append(ItemPorUser(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6])))
    for x in i2:
        b = x.split(" ")
        item2.append(ItemPorUser(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6])))
    resultado = Combate(user1, user2, item1, item2, -1, 0)
    print(" Resultado = " + str(resultado))
    # resultado = 0 -> Empate
    # resultado = 1 -> u1 venceu
    # resultado = 2 -> u2 venceu
    # Enviar resultado para o utilizador
    # Actualizar a base de dados com vitórias/etc
    # Actualizar a base de dados com xp para os users consoante o resultado


<<<<<<< HEAD
"""
Testes:
"""


def GerarUser():
    a = 0
    s = ""
    while a < 5:
        s = s + " " + str(rand.randint(0, 10))
        a = a +1
    return s + "\n"


# TesteUser1 = "0 5 6 7 8 9\n"
# TesteUser2 = "1 9 8 7 6 5\n"
TesteUser1 = "0" + GerarUser()
TesteUser2 = "1" + GerarUser()

TesteItems1 = "1 2 0 3 0 2 0\n2 0 3 0 5 1 0\n"
TesteItems2 = "3 5 0 3 1 2 1\n4 0 2 0 1 5 1\n"
find_enemy(0, "ID", 1)

# Status -> output = fun.reduce(lambda x,y: x+f" {rand.randint(int(y)-10,int(y)+10)}",Teste1[1:],str(Teste1[0]))
# itens  -> output = fun.reduce(lambda x,y: x+f" {rand.randint(int(y)-10,int(y)+10)}",Teste1[1:-1],str(Teste1[0])) + " " + str(Teste1[-1])
=======
>>>>>>> bb96567ec4745b7139d930433989204af264aa82
