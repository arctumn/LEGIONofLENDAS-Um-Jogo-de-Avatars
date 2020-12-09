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


# Precisas englobar isso tudo dentro de uma função bc isso é chamado para diferentes players
# trata de encorporar isso dentro do find_enemy depois é so somares ou usares o user2/itens/stats e user1/itens/stats



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
        err, user2_stats = get_stats(uid)
        if err:
            print(err)
            return err, False
    
    elif operation_context == "BOT" :
        #faz a matematica para os status do bot
        def calcStatus(x,y):
            y = " " + str(rand.randint(int(y)-10,int(y)+10))
            return x+y

        # isto é os itens do user2 because BOT u1itens = u2itens
        user2_itens = user1_itens
        # isto é os id do user2 + status do user 2 separados por espaço
        # example input lista = ["5", "10", "15", "20", "25", "30"] output = "5 0 6 16 22 28"
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


#
items = ""  # Linha com items do 1o e 2o utilizador
users = ""  # Linha com stats do 1o e 2o utilizador


def controloResults(u, dn, dm):
    # Enviar para o utilizador os resultados da ronda de combate:
    # u.hp = vida actual do user atacado
    # dn = dano normal
    # dm = dano mágico
    pass


# Calcula o dano de um utilizador
def calculoDano(u, it):
    print("A calcular dano")
    # Criar loop para calcular com várias armas:
    dano[0] = u.stren  # Dano fisico
    dano[1] = u.mag  # Dano mágico
    for x in it:  # Percorre as armas
        dano[0] = dano[0] + u.stren * x.stren
        dano[1] = dano[1] + u.mag * x.mag
    return dano  # Possibilidade de adicionar % aleatórios de dano bónus/retirado?, exemplo (pseudo-código):
    # (dano[0] * random(0.95, 1.05), dano[1] * random(0.95, 1.05)
    # return dano


def Combate(u1, u2, it1, it2, pa):
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
    d1 = calculoDano(u1, it1)
    d2 = calculoDano(u2, it2)
    if pa == -1:  # Primeira execução, escolhe um para começar aleatóriamente
        pa = randint(1, 2)
    defesa[0] = 0
    defesa[1] = 0

    if pa == 1:  # User 1 é o primeiro a atacar
        for x in it1:
            defesa[0] = defesa[0] + x.defn
            defesa[1] = defesa[1] + x.defm
        dadosDN = (d1[0] - defesa[0])
        dadosDM = (d1[1] - defesa[1])
        if dadosDN > 0:
            dadosDN = 0
        if dadosDM > 0:
            dadosDM = 0
        u2.hp = u2.hp - (dadosDN + dadosDM)
        pa = 2
        ControloResults(u2, dadosDN, dadosDM)
        # Obs na minha opinião poderíamos mudar a defesa
        # para ser feita com base em %.

    if pa == 2:  # User 2 é o primeiro a atacar
        for x in it2:
            defesa[0] = defesa[0] + x.defn
            defesa[1] = defesa[1] + x.defm
        dadosDN = (d2[0] - defesa[0])
        dadosDM = (d2[1] - defesa[1])
        if dadosDN > 0:
            dadosDN = 0
        if dadosDM > 0:
            dadosDM = 0
        u1.hp = u1.hp - (dadosDN + dadosDM)
        pa = 1
        ControloResults(u1, dadosDN, dadosDM)

    Combate(u1, u2, it1, it2, pa)


# Função Principal do decorrer do combate
def inicioCombate():
    # Fazer query da string users e da string items

    print("Inicio do Combate")
    user1 = Combatente
    user2 = Combatente
    a = users.split("\n")
    y = 0
    for x in a:  # Cria os objectos combatentes
        if y == 0:
            b = x.split(" ")
            user1 = Combatente(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6]))
        if y == 1:
            b = x.split(" ")
            user2 = Combatente(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6]))
        y = y + 1
    item1 = ItemPorUser
    item2 = ItemPorUser
    a = items.split("\n")
    y1 = 0
    y2 = 0
    for x in a:  # Cria os objectos dos items e distribui por utilizador
        b = x.split(" ")
        if int(b[6]) == user1.idu:
            item1[y1] = ItemPorUser(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6]))
            y1 = y1 + 1
        if int(b[6]) == user1.idu:
            item2[y2] = ItemPorUser(int(b[1]), int(b[2]), int(b[3]), int(b[4]), int(b[5]), int(b[6]))
            y2 = y2 + 1
    resultado = Combate(user1, user2, item1, item2, -1)
    # resultado = 0 -> Empate
    # resultado = 1 -> u1 venceu
    # resultado = 2 -> u2 venceu
    # Enviar resultado para o utilizador
    # Actualizar a base de dados com vitórias/etc
    # Actualizar a base de dados com xp para os users consoante o resultado


