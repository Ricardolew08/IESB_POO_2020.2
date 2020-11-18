package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemAjudante(
    classeAjudante: Int,
    nomeAjudante: String,
    elementoAjudante: Int,
    jogadorBase: PersonagemJogador,
    rpg: Rpg
) : Personagem(nomeAjudante, elementoAjudante) {

    // 0 - Escudeiro; 1 - Mago; 2 - Mestre das Cartas;
    var classe: Int = classeAjudante
    var batalhas: Int = 0

    init {

        id = genId(rpg)
        classe = classeAjudante


        this.dinheiro = 50

        this.nivel = (1..(jogadorBase.nivel) + 2).random()

        if (classeAjudante == 0) {
            this.ataque = 0
            this.defesa = ((1..3).random() * this.nivel)
        } else if (classeAjudante == 1) {
            if (elemento % 2 == 0) {
                this.ataque = ((4..5).random() * this.nivel)
                this.defesa = jogadorBase.defesa - 1
            } else {
                this.defesa = ((4..5).random() * this.nivel)
                this.ataque = jogadorBase.ataque - 1
            }
        } else {
            this.ataque = 0
            this.defesa = 0
            this.sorte = 10
        }
    }

    override fun genId(rpgAtual: Rpg): Int {
        var novaId = (0..10000).random()
        while (rpgAtual.ajudante.find { it.id == novaId } != null) {
            novaId = (0..10000).random()
        }
        return novaId
    }

    fun encerrarcontrato(rpg: Rpg, jogador: PersonagemJogador) {
        var ajudante = rpg.ajudante.find { it.id == this.id }
        println(ajudante)
        rpg.ajudante.remove(rpg.ajudante.find { it.id == this.id })
        jogador.ajudanteAtual.remove(jogador.ajudanteAtual.find { it.id == this.id })
    }

}