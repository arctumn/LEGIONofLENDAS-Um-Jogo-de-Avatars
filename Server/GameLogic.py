import functools as fun
import random as rand

import DataBase as db



def get_itens(id):  # Vai buscar os items
    err, userID_itens = db.run_query(id,f"SELECT * FROM inventario WHERE iduser = {id};")  # idarma + str + mag + def + defm + hp + iduser
    if userID_itens == "":
        return False, ""
    userID_itens = userID_itens.split("\n")
    return err, userID_itens


def get_stats(id):  # Vai buscar os stats
    err, userID_status = db.run_query(id, f"SELECT * FROM status WHERE ID = {id};")
    if userID_status == "":
        return False, ""
    #print(userID_status)
    #print(err)    
    userID_status = userID_status.split("\n")
    return err, userID_status


def randomuser(id1, userSTAT):
    query = f"SELECT ID FROM status WHERE \
    forca > {int(userSTAT[0]) - 10} AND forca < {int(userSTAT[0]) + 10} \
    AND magia > {int(userSTAT[1]) - 10} AND magia < {int(userSTAT[1]) + 10} \
    AND defesa > {int(userSTAT[2]) - 10} AND defesa < {int(userSTAT[2]) + 10} \
    AND defesaMagica > {int(userSTAT[3]) - 10} AND defesaMagica < {int(userSTAT[3]) + 10} \
    AND vida > {int(userSTAT[4]) - 10} AND vida < {int(userSTAT[4]) + 10} AND ID != {id1};"

    err, elements = db.run_query(id1, query)
    print(f"Valor de err = [{err}] dentro do randomuser\n Valores de elements = [{elements}]")
    if elements == "":
        return "TRYBOT", False
    listofchoosen = elements.split("\n")
    return False, (listofchoosen[rand.randint(0, len(listofchoosen) - 1)])


# Precisas englobar isso tudo dentro de uma função bc isso é chamado para diferentes players
# trata de encorporar isso dentro do find_enemy depois é so somares ou usares o user2/itens/stats e user1/itens/stats


def find_enemy(id1, operation_context):
    # operation context = PLAYER (um player aleatorio)
    # operation context = BOT (pegas nas infos do primeiro e fazes um rand entre -10 e + 10 de todos os status do player)
    # operation context = ID (Player através do QR code)
    # itens u1
    
    err, user1_itens = get_itens(id1)
    #print("AQUI")
    if err:
        print(err)
        return err, False
    user1_itens = user1_itens[:-1]
    # status u1
    # print("ALI")
    err, user1_status = get_stats(id1)
    # print(f"Valor de err: {err} e valor de itens: {user1_itens}")
    # Versao de teste:
    # user1_status = TesteUser1.split("\n")[:-1]

    if err:
        print(err)
        return err, False

    #print("AQUI2")    
    user1_status = user1_status[:-1]
    user1STAT = ((user1_status[0]).split(" "))[1:6]
    # print("ALI2")

    # s = ""
    # for a in user1STAT:
    #    s = s + str(a) + " "
    # print("u1 carregado com os seguintes stats: " + s)
    test = str(operation_context).rstrip("\n")
    #print(f"Valor de teste = {test}")
    if test == "Player":
        #print("PLAYER")
        err, uid = randomuser(id1, user1STAT)
        print(f"Valor de err = {err}\nValor de uid = {uid}")
        if err == "TRYBOT":
            find_enemy(id1, "Bot")
            # itens u2
        #print("Antes do get_itens")
        err, user2_itens = get_itens(uid)
        if err:
            #print(err)
            return err, False
        #print(user2_itens)
            # status u2
        user2_itens = user2_itens[:-1]
        #print("Antes do get_stats")
        err, user2_stats = get_stats(uid)
        if err:
            #print(err)
            return err, False
        #print(user2_stats)
        user2_stats = user2_stats[:-1]
        #print(user2_stats)
        user2STAT = ((user2_stats[0]).split(" "))[1:6]
        #print(user2STAT)
        # s = ""
        # for a in user2STAT:
        #    s = s + str(a) + " "
        # print("u2 carregado com os seguintes stats: " + s)
        return inicioCombate(user1STAT, user2STAT, user1_itens, user2_itens, id1, uid, 0)

    elif test == "Bot":
        # faz a matematica para os status do bot
        def calcStatus(x, y):
            y = " " + str(rand.randint(int(y) - 10, int(y) + 10))
            return x + y
        #print("DENTRO DO BOT")
        # isto é os itens do user2 because BOT u1itens = u2itens
        user2_itens = user1_itens
        # isto é os id do user2 + status do user 2 separados por espaço
        # example input lista = ["5", "10", "15", "20", "25", "30"] output = "5 0 6 16 22 28"
        user2_stats = fun.reduce(calcStatus, user1STAT[1:], user1STAT[0]).split(" ")
        #print(user2_stats)
        #print("POS FORMAT")
        return inicioCombate(user1STAT, user2_stats, user1_itens, user2_itens, id1, id1, 1)

    else:
        # itens u2
        operation_context = str(operation_context).rstrip("\n")
        #print("Value of opc: ["+operation_context+"] ")
        err, user2_itens = get_itens(operation_context)
        #print("CHEGUEI ANTES DO INICIO DO COMBATE")
        if err:
            #print(err)
            return err, False
            # status u2
        user2_itens = user2_itens[:-1]
        err, user2_stats = get_stats(operation_context)
        if err:
            #print(err)
            return err, False
        user2_stats = user2_stats[:-1]
        user2STAT = ((user2_stats[0]).split(" "))[1:6]
        #print("CHEGUEI ANTES DO INICIO DO COMBATE")
        # s = ""
        # for a in user2STAT:
        #    s = s + str(a) + " "
        # print("u2 carregado com os seguintes stats: " + s)
        return inicioCombate(user1STAT, user2STAT, user1_itens, user2_itens, id1, operation_context, 2)


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

def controloResults(u1, u2, d, n, s):
    if n == 0:  # ronda 0: id1 + id2 + hp1 + n + hp2
        s = str(u1.idu) + " " + str(u2.idu) + " " + str(u1.hp) + " " + str(n) + " " + str(u2.hp) + "\n"
        return s
    else:
        s = s + str(u1.idu) + " " + str(u1.hp) + " " + str(d) + " " + str(n) + "\n"
    # Enviar para o utilizador os resultados da ronda de combate:
    # u1 = user atacado
    # d = dano total
    # n = nº de ronda
    return s


# Calcula o dano de um utilizador
def calculoDano(u, it):
    #print("A calcular dano")
    dano = []
    # Criar loop para calcular com várias armas:
    dano.append(u.stren)  # Dano fisico
    dano.append(u.mag)  # Dano mágico
    for x in it:  # Percorre as armas
        dano[0] = dano[0] + x.stren
        dano[1] = dano[1] + x.mag
    return dano  # Possibilidade de adicionar % aleatórios de dano bónus/retirado?, exemplo (pseudo-código):
    # (dano[0] * random(0.95, 1.05), dano[1] * random(0.95, 1.05)
    # return dano


def finalDamage(damage, defesaValue):
    if damage == 0:
        damage = rand.randint(0, 10)
    elif 0 < damage <= 10:
        damage = damage + rand.randint(int(damage * -0.5), int(damage * 0.5))
    elif 10 < damage <= 50:
        damage = damage + rand.randint(int(damage * -0.3), int(damage * 0.3))
    elif 50 < damage:
        damage = damage + rand.randint(int(damage * -0.2), int(damage * 0.2))
    balance = defesaValue / damage
    #print("damage = " + str(damage) + " balance: " + str(balance))

    fd = -1

    if balance == 0:
        fd = damage
    elif 0 < balance <= 0.2:
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
    #print("CALCDANO, recebi: magicDamage -> " + str(magicDamage) +
    #      ", magicDefense -> " + str(magicDefense) +
    #      ", physicDamage -> " + str(physicDamage) +
    #      ", physicDefense -> " + str(physicDefense))

    magicDamage = finalDamage(magicDamage, magicDefense)
    physicDamage = finalDamage(physicDamage, physicDefense)
    return magicDamage * 0.5 + physicDamage * 0.5


def Combate(u1, u2, it1, it2, pa, numero_ronda, string_output_ronda):  # Pa -> Primeiro a Atacar

    if numero_ronda == 0:
        string_output_ronda = controloResults(u1, u2, 0, numero_ronda, string_output_ronda)
        numero_ronda = 1

    #print(" A iniciar ronda: " + str(numero_ronda))
    if u1.hp <= 0:
        #print("acabar combate, u2 venceu")
        return 2, str(string_output_ronda)
    elif u2.hp <= 0:
        #print("acabar combate, u1 venceu")
        return 1, str(string_output_ronda)
    else:

    # d1 = calculoDano(u1, it1)
    # d2 = calculoDano(u2, it2)

        if pa == -1:  # Primeira execução, escolhe um para começar aleatóriamente
            pa = rand.randint(1, 2)
            #print("Escolhido o utilizador u" + str(pa))

        if pa == 1:  # User 1 é o primeiro a atacar
            #print("A iniciar o ataque de u1...")
            defesa = [0, 0]
            d = calculoDano(u1, it1)
           #print("Dano = " + str(d[0]) + ", dano mágico = " + str(d[1]))

            for x in it2:
                defesa[0] = defesa[0] + x.defn
                defesa[0] = defesa[1] + x.defm
            #print("Defesa do adversário = " + str(defesa[0]) + ", defesa mágica = " + str(defesa[1]))
            fd = damageDone(d[1], defesa[1], d[0], defesa[0])

            #print("Dano = " + str(fd))
            if fd < 0:
                #print("Vida antes do ataque (u1): " + str(u1.hp))
                u1.hp = u1.hp + fd
                #print("Vida depois do ataque (u1): " + str(u1.hp))
            else:
                #print("Vida antes do ataque (u2): " + str(u2.hp))
                u2.hp = u2.hp - fd
                #print("Vida depois do ataque (u2): " + str(u2.hp))
            pa = 2
            string_output_ronda = controloResults(u2, u1, fd, numero_ronda, string_output_ronda)
            # Obs na minha opinião poderíamos mudar a defesa
            # para ser feita com base em %.

        else:  # User 2 é o primeiro a atacar
            #print("A iniciar o ataque de u2...")
            defesa = [0, 0]
            d = calculoDano(u2, it2)
            #print("Dano = " + str(d[0]) + ", dano mágico = " + str(d[1]))
    
            for x in it1:
                defesa[0] = defesa[0] + x.defn
                defesa[1] = defesa[1] + x.defm
            #print("Defesa do adversário = " + str(defesa[0]) + ", defesa mágica = " + str(defesa[1]))
            fd = damageDone(d[1], defesa[1], d[0], d[1])
    
            #print("Dano = " + str(fd))
            if fd < 0:
                #print("Vida antes do ataque (u2): " + str(u2.hp))
                u2.hp = u2.hp + fd
               #print("Vida depois do ataque (u2): " + str(u2.hp))
            else:
                #print("Vida antes do ataque (u1): " + str(u1.hp))
                u1.hp = u1.hp - fd
                #print("Vida depois do ataque (u1): " + str(u1.hp))
            pa = 1
            string_output_ronda = controloResults(u1, u2, fd, numero_ronda, string_output_ronda)
            #print(f"VALOR DE S --------->[{string_output_ronda}]")
    return Combate(u1, u2, it1, it2, pa, numero_ronda + 1, string_output_ronda)
          

def Recompensar(uid, x):
    #print("ENTREI NO RECOMPENSAR")
    _, xp = db.run_query(uid, f"SELECT xp FROM user WHERE id = {uid};")

    xp = xp.split("\n")
    db.conn.execute(f"UPDATE user SET xp = {int(x) + int(xp[0])} WHERE id = {uid};")
    db.conn.commit()
    #print("FIM")
    return True

def adicionarVitoria(id):
    _, vic = db.run_query(id, f"SELECT vitorias FROM status WHERE id = {id};")
    vic = vic.split("\n")
    print(vic)
    db.conn.execute(f"UPDATE status SET vitorias = {1 + int(vic[0])} WHERE id = {id};")
    db.conn.commit()


def adicionarDerrota(id):
    _, der = db.run_query(id, f"SELECT derrotas FROM status WHERE id = {id};")
    der = der.split("\n")
    print(der)
    db.conn.execute(f"UPDATE status SET derrotas = {1 + int(der[0])} WHERE id = {id};")
    db.conn.commit()

def Notificar(uid, s):
    cid = 0
    cursor = db.conn.execute("SELECT id FROM batalhaLOG ORDER BY id DESC LIMIT 1;")
    for row in cursor:
        if row[0]+1:
            cid = row[0] + 1
            
    #print(f"VALORES: [{cid}, {s}, {uid}, {0}]")
    db.conn.execute(f"INSERT INTO batalhaLOG (id, texto, idUSER, visto) VALUES ({cid},'{s}',{uid},{0})")
    _ = db.conn.commit()
    #print("OLA")
# Função Principal do decorrer do combate
def inicioCombate(u1, u2, i1, i2, id1, id2, tipo):
    # Fazer query da string users e da string items

    #print("Inicio do Combate entre user1 -> " + str(id1) + " e user2 -> " + str(id2))
    s = ""
    # Cria os objectos combatentes
    #print(u1)
    #print(u2)
    user1 = Combatente(int(u1[0]), int(u1[1]), int(u1[2]), int(u1[3]), int(u1[4]), int(id1))
    #print(user1)
    user2 = Combatente(int(u2[0]), int(u2[1]), int(u2[2]), int(u2[3]), int(u2[4]), int(id2))
    #print(user2)
    # item[x] =  idarma + str + mag + def + defm + hp + iduser
    item1 = []
    item2 = []
    for x in i1:  # Cria os objectos dos items e distribui por utilizador
        #print(x)
        b = x.split(" ")
        item1.append(ItemPorUser(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6])))
    for x in i2:
        b = x.split(" ")
        item2.append(ItemPorUser(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6])))
    resultado,s = Combate(user1, user2, item1, item2, -1, 0, s)
    #print(" Resultado = " + str(resultado))

    # Enviar s para os users user1.idu e user2.idu
    if tipo == 1:  # BOT
        Notificar(user1.idu, s)
        #print(user1.idu)
        if resultado == 1:
            Recompensar(user1.idu, 4)
        else:
            Recompensar(user1.idu, 2)

    elif tipo == 2:  # ID
       # print("ANTES DE NOTIFICAR")
        Notificar(user1.idu, s)
        Notificar(user2.idu, s)
        #print("DEPOIS DE NOTIFICAR")
        if resultado == 1:
            Recompensar(user1.idu, 3)
            Recompensar(user2.idu, 1)

        else:
            Recompensar(user2.idu, 3)
            Recompensar(user1.idu, 1)


    else:  # PLAYER
        Notificar(user1.idu, s)
        if resultado == 1:
            #print("ANTESVITORIA")
            adicionarVitoria(user1.idu)
            #print("DEPOISVITORIA")
            adicionarDerrota(user2.idu)
            #print("ANTESVITORIA")
            Recompensar(user1.idu, 6)
            Recompensar(user2.idu, 3)
            
        else:
            #print("ANTESVITORIA")
            adicionarVitoria(user2.idu)
            #print("DEPOISVITORIA")
            adicionarDerrota(user1.idu)
            #print("ANTESVITORIA")
            Recompensar(user2.idu, 6)
            Recompensar(user1.idu, 3)
            
    # resultado = 0 -> Empate
    # resultado = 1 -> u1 venceu
    # resultado = 2 -> u2 venceu
    # Enviar resultado para o utilizador
    # Actualizar a base de dados com vitórias/etc
    # Actualizar a base de dados com xp para os users consoante o resultado
