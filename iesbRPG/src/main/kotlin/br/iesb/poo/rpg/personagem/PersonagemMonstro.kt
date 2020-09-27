package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg
import kotlin.math.ceil

class PersonagemMonstro(
        novaRaca: Int = -1,
        nomeMonstro: String = "PLACEHOLDER",
        elementoMonstro: Int = -1,
        jogadorBase: PersonagemJogador,
        rpgAtual: Rpg
) : Personagem(nomeMonstro, elementoMonstro) {

    // Orc = 0; Goblin = 1; Guinomio = 2
    // Orc + Defesa; Goblin + Ataque

    var raca: Int = 0

    init {
        id = genId(rpgAtual)
        raca = novaRaca
        this.nivel = (1..(jogadorBase.nivel) + 2).random()

        if (novaRaca != 2) {

            dinheiro = (this.nivel * (1..2).random())

            if (raca == 1) {
                if (elemento % 2 == 0) {
                    this.ataque = ((1..2).random() * this.nivel)
                    this.defesa = (1 * this.nivel)
                } else {
                    this.ataque = (2 * this.nivel)
                    this.defesa = (2 * this.nivel)
                }
            } else {
                if (elemento % 2 == 0) {
                    this.ataque = (2 * this.nivel)
                    this.defesa = (2 * this.nivel)
                } else {
                    this.ataque = (1 * this.nivel)
                    this.defesa = ((1..2).random() * this.nivel)
                }
            }

        } else {
            this.ataque = 0
            this.defesa = (this.nivel * 10)
            dinheiro = (this.nivel) * (this.nivel * (2..3).random())
        }


    }

    override fun genId(rpgAtual: Rpg): Int {
        var novaId = (0..10000).random()
        while (rpgAtual.monstros.find{it.id == novaId} != null){
            novaId = (0..10000).random()
        }
        return novaId
    }

    override fun derrota(rpg: Rpg): String {
        rpg.jogadores.remove(rpg.jogadores.filter { it.id == this.id }[0])
        return ""
    }

}