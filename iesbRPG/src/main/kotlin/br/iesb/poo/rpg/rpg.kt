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
    PERSONAGEM_MESTRE,
    PERSONAGEM_CHEFE
}

class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()
    val ajudante = mutableListOf<PersonagemAjudante>()

    private val listaNomes = arrayOf(
        "Bei, ",
        "Thok, ",
        "Mashbu, ",
        "Lurtz, "
    )

    private val listaCoringa = arrayOf(
        "BUG",
        "Dorminhoco",
        "Enjoado",
        "Feiticeiro da cor roxa de um arco íris"
    )

    private val listaTitulos = arrayOf(
        "o Comilão",
        "o Banguela",
        "o Preguiçoso",
        "o Destraido"
    )

    private val listaChefe = arrayOf(
        "Blexoverreth, o Terrivel",
        "Nedraco, o não Bonito",
        "Hanthad, o Perverso",
        "Kenniston Funkeiro",
        "P3, a Prova"
    )

    private val listaAjudantes = arrayOf(
        "Snake Luquinhas",
        "Sir Pancho",
        "Estouro de Pilha"
    )

    fun criarMonstro(
        tipoPersonagem: TipoPersonagem,
        jogadorBaseBatalha: PersonagemJogador
    ): PersonagemMonstro {

        val novoPersonagem = if (tipoPersonagem == TipoPersonagem.PERSONAGEM_CORINGA) {
            PersonagemMonstro(
                novaRaca = 2,
                (listaCoringa).random(),
                elementoMonstro = (1..4).random(),
                jogadorBase = jogadorBaseBatalha,
                RPG
            )
        } else if (tipoPersonagem == TipoPersonagem.PERSONAGEM_MONSTRO){
            PersonagemMonstro(
                novaRaca = (0..1).random(),
                (listaNomes).random() + (listaTitulos).random(),
                elementoMonstro = (1..4).random(),
                jogadorBase = jogadorBaseBatalha,
                RPG
            )
        } else{
            PersonagemMonstro(
                novaRaca = 3,
                (listaChefe).random(),
                elementoMonstro = 1,
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
