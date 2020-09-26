package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemMonstro(
    novaRaca: Int = -1,
    nomeMonstro: String = "PLACEHOLDER",
    elementoMonstro: Int = -1,
    jogadorBase: PersonagemJogador,
    idNumero: Int
) : Personagem(nomeMonstro, elementoMonstro, idNumero) {

    // Orc = 1; Goblin = 2
    // Orc + Defesa; Goblin + Ataque

    var raca: Int = 0

    init {
        raca = novaRaca

        this.ataque += if (jogadorBase.ataque >= 3) {
            (jogadorBase.ataque - 2..jogadorBase.ataque + 1).random()
        } else {
            (0..jogadorBase.ataque + 1).random()
        }

        this.defesa += if (jogadorBase.defesa >= 3) {
            (jogadorBase.defesa - 2..jogadorBase.defesa + 1).random()
        } else {
            (0..jogadorBase.defesa + 1).random()
        }
    }

    override fun genId(Seed: Int): Int { //Fazer uma função muito louca eventualmente
        return Seed.plus(1)
    }

    override fun derrota(rpg: Rpg): String {
        rpg.jogadores.remove(rpg.jogadores.filter { it.id == this.id }[0])
        return ""
    }

}