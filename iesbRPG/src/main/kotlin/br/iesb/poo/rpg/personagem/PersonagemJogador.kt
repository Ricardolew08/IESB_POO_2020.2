package br.iesb.poo.rpg.personagem

import java.util.zip.DeflaterOutputStream
import kotlin.math.floor
import kotlin.math.roundToInt

class PersonagemJogador( classejogador: Int,
//                       vida: Int,
//                       sorte: Int, Como usuário nao escolha nao precisamos receber
                         nick: String, //filho tem tudo que o pai tem
                         element: Int) : Personagem(nick,element) {

    //Arqueiro = 1; Cavaleiro = 2
    //Arqueiro + Ataque; Cavaleiro + Defesa

    var classe: Int = 0

    var vida: Int = 5

    var sorte: Int = 0

    var xp: Int = 0

    init {
        classe = classejogador
    }


    open fun Morrer() {
        println("You died bitch!")
    }

    open fun Perder() {
        this.vida--
        this.dinheiro.times(((5..9).random()) / 10)
        println("vida perdida:  ${this.vida}" )
        if (this.vida == 0) {
            this.Morrer()
        } else {
            println("${nome} foi abatido. \n" +
                    "Perdeu 1 de vida e ${this.dinheiro} moedas de ouro")
        }

    }

    open fun Ganhar() {
        this.dinheiro += (this.nivel * (1..3).random())
        println("Total moedas: ${this.dinheiro}")
        val xpganho = 500
        this.xp += xpganho
//        this.nivel.times((2..3).random())//-log x+2
        println("Nível: ${this.nivel}")
        if (xpganho + this.xp >= 500 * this.nivel) {
            this.nivel += xpganho/500
//            val x : Double = this.nivel
            this.Upnivel()
            println("Upou nivel")
        }
        if ((1..10).random() + this.sorte >= 9) {
            this.vida.plus(1)
            println("Ganhou poção de vida.")
        }
        if (this.sorte<=2 && (1..100).random() == 1){
            this.sorte.plus(1)
            println("Ganhou sorte.")
        }

        println("Monstro foi abatido. \n" +
                "Ganhou ${dinheiro} moedas de ouro")

    }

    open fun Upnivel(){

        if (classe == 1) {
            if (elemento % 2 == 0){
                this.ataque += (3 * this.nivel )
                this.defesa += (1 * this.nivel )
            }else{
                this.ataque += (2 * this.nivel)
                this.defesa += (2 * this.nivel)
            }

        } else {
            if (elemento % 2 == 0){
                this.ataque += (2 * this.nivel)
                this.defesa += (2 * this.nivel)
            }else{
                this.ataque += (1 * this.nivel )
                this.defesa += (3 * this.nivel )
            }
        }



        if(vida<5)
            this.vida++


    }
}