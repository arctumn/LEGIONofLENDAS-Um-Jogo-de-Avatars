def find_enemy(operation_context):
    return False, "Found Enemy"


class Combatente:  # Warriors
    def __init__(self, str, mag, defn, defm, hp, idu):
        self.str = str
        self.mag = mag
        self.defn = defn
        self.defm = defm
        self.hp = hp
        self.idu = idu


class ItemPorUser:  # Items
    def __init__(self, str, mag, defn, defm, hp, idu):
        self.str = str
        self.mag = mag
        self.defn = defn
        self.defm = defm
        self.hp = hp
        self.idu = idu


# Item order: id + força + magia + def + defm + hp + userid
# User: id + str + mag + def + defm + hp

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
    dano[0] = u.str  # Dano fisico
    dano[1] = u.mag  # Dano mágico
    for x in it:  # Percorre as armas
        dano[0] = dano[0] + u.str * x.str
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
