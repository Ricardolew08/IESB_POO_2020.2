package br.iesb.poo.rpg.personagem

import kotlin.math.floor
import kotlin.math.roundToInt

class PersonagemJogador : Personagem() {

    //Arqueiro = 1; Cavaleiro = 2
    //Arqueiro + Ataque; Cavaleiro + Defesa

    var classe: Int = 0

    var vida: Int = 5

    var sorte: Int = 0

    open fun Morrer() {
        println("You died bitch!")
    }

    open fun Perder() {
        this.vida.minus(1)
        this.dinheiro.times(((5..9).random()) / 10)
        if (this.vida == 0) {
            this.Morrer()
        } else {
            println("${nome} foi abatido. \n" +
                    "Perdeu 1 de vida e ${dinheiro} moedas de ouro")
        }

    }

    open fun Ganhar() {
        this.dinheiro.plus(this.nivel * (1..3).random())
        val niveltemp = (1..3).random() * this.nivel
        this.nivel.times((1..3).random())//-log x+2
        if (floor(niveltemp) > floor(this.nivel)) {
            this.Upnivel()
        }
        if ((1..10).random() + this.sorte >= 9) {
            println("Parabéns, aproveite sua poção de vida! Bad bitch")
            this.vida.plus(1)
        }

    }

    open fun Upnivel() {

        if (classe == 1) { //Relacionar clase de acordo com elementos
            this.ataque += 2
            this.defesa += 1
        } else {
            this.defesa += 2
            this.ataque += 1
        }
    }
}