package br.iesb.poo.rpg

import br.iesb.poo.RPG
import br.iesb.poo.rpg.personagem.PersonagemAjudante
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import br.iesb.poo.rpg.taverna.Taverna

enum class TipoPersonagem {
    PERSONAGEM_MONSTRO,
    PERSONAGEM_CORINGA,
    PERSONAGEM_AJUDANTE,
    PERSONAGEM_MESTRE
}

class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()
    val ajudante = mutableListOf<PersonagemAjudante>()

    private val listaNomes = arrayOf(
        "Valdomiro Putão",
        "Bozonaru",
        "Lula d'Asilva Bushim Shei al Mussei",
        "Donarudu Trumpuru",
        "Montro",
        "Éffiagácê",
        "Kin John 1: Flango",
        "Kin John 2: return('The')",
        "Conde Temer",
        "Abaishar, o Assado"
    )

    private val listaAjudantes = arrayOf(
        "Um nome bonitinho"
    )

    fun criarMonstro(
        tipoPersonagem: TipoPersonagem,
        jogadorBaseBatalha: PersonagemJogador
    ): PersonagemMonstro {
        val novoPersonagem = if (tipoPersonagem == TipoPersonagem.PERSONAGEM_CORINGA) {
            PersonagemMonstro(
                novaRaca = 2,
                (listaNomes).random(),
                elementoMonstro = -1,
                jogadorBase = jogadorBaseBatalha,
                RPG
            )
        } else {
            PersonagemMonstro(
                novaRaca = (0..1).random(),
                (listaNomes).random(),
                elementoMonstro = (1..4).random(),
                jogadorBase = jogadorBaseBatalha,
                RPG
            )
        }
        monstros.add(novoPersonagem)
        return novoPersonagem
    }

    fun criarAjudante(
        tipoPersonagem: TipoPersonagem,
        jogadorBaseTaverna: PersonagemJogador): PersonagemAjudante{
        val novoPersonagem = if (tipoPersonagem == TipoPersonagem.PERSONAGEM_MESTRE){
            PersonagemAjudante(2, (listaAjudantes).random(), -1, jogadorBaseTaverna, RPG)
        }else{
            PersonagemAjudante((0..1).random(), (listaAjudantes).random(), (1..4).random(), jogadorBaseTaverna, RPG)
        }
        ajudante.add(novoPersonagem)
        return novoPersonagem
    }
}
