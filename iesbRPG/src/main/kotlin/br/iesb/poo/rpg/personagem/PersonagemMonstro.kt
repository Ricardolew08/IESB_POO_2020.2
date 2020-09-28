package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemMonstro(
        novaRaca: Int,
        nomeMonstro: String,
        elementoMonstro: Int,
        jogadorBase: PersonagemJogador,
        rpgAtual: Rpg
) : Personagem(nomeMonstro, elementoMonstro) {

    // Orc = 0; Goblin = 1; Gnomio = 2
    // Orc + Defesa; Goblin + Ataque

    var raca: Int = 0
    var derrotado = false

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
        while (rpgAtual.monstros.find{it.id == novaId} != null){ //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    override fun derrota(rpg: Rpg): String {
        this.derrotado = true
        return "[ c: ] ${this.nome} FOI DERROTADO\n"
    }
}