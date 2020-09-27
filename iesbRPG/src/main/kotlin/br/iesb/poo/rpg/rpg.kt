package br.iesb.poo.rpg

import br.iesb.poo.RPG
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro

enum class TipoPersonagem { //enum basicamente criar classes no sentido de enumeração
    PERSONAGEM_MONSTRO,
    PERSONAGEM_CORINGA
}

class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()



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
}
