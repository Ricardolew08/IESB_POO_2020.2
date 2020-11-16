package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemAjudante (classeAjudante: Int,
                          nomeAjudante: String,
                          elementoAjudante: Int,
                          jogadorBase: PersonagemJogador,
                          rpg: Rpg
): Personagem(nomeAjudante, elementoAjudante) {

    // 0 - Escudeiro; 1 - Mago; 2 - Mestre das Cartas;
    var classe: Int = classeAjudante
    var batalhas: Int = 0

    init {

        id = genId(rpg)
        classe = classeAjudante


        this.dinheiro = 50

        this.nivel = (1..(jogadorBase.nivel) + 2).random()

        if (classeAjudante == 0) {
            this.defesa = ((1..3).random() * this.nivel)
        } else if (classeAjudante == 1){
            if (elemento % 2 == 0) {
                this.ataque = ((1..2).random() * this.nivel)
            } else {
                this.defesa = ((1..2).random() * this.nivel)
            }
        } else {
            this.sorte = ((0..4).random() * this.nivel)
        }
    }

    override fun genId(rpgAtual: Rpg): Int {
        var novaId = (0..10000).random()
        while (rpgAtual.ajudante.find{it.id == novaId} != null){ //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    fun encerrarcontrato(rpg:Rpg,jogador: PersonagemJogador){
        var ajudante = rpg.ajudante.find { it.id == this.id }
        println(ajudante)
        rpg.ajudante.remove(rpg.ajudante.find { it.id == this.id })
        jogador.ajudanteAtual.remove(jogador.ajudanteAtual.find{ it.id == this.id })


    }
}