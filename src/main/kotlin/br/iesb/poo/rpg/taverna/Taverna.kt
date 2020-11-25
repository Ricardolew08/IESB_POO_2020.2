package br.iesb.poo.rpg.taverna

import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem
import br.iesb.poo.rpg.personagem.Personagem
import br.iesb.poo.rpg.personagem.PersonagemAjudante
import br.iesb.poo.rpg.personagem.PersonagemJogador

//contratação de ajudantes

class Taverna () {

    fun criarJogador(jogador: PersonagemJogador, RPG : Rpg): PersonagemAjudante{


        val ajudante: PersonagemAjudante = if ((1..100).random() >=5) {
            RPG.criarAjudante(TipoPersonagem.PERSONAGEM_AJUDANTE, jogador)
        } else {
            RPG.criarAjudante(TipoPersonagem.PERSONAGEM_MESTRE, jogador)
        }

        return ajudante
    }

}
