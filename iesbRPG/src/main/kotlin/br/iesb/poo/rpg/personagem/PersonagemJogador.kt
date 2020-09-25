package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemJogador(
    classeJogador: Int,
    nomeJogador: String,
    elementoJogador: Int,
    idNumero: Int
) : Personagem(nomeJogador, elementoJogador) {

    //Arqueiro = 1; Cavaleiro = 2
    //Arqueiro + Ataque; Cavaleiro + Defesa

    var id: Int = idNumero

    var classe: Int = classeJogador

    var sorte: Int = 0
    private var vida: Int = 1
    private var xp: Int = 0

    /*
    override fun genId(Seed: Int): Int { //Fazer uma função muito louca eventualmente
        return Seed.plus(1)
    }
    */

    private fun morrerJogador(rpg: Rpg) {

        rpg.jogadores.remove(rpg.jogadores.filter { it.id == this.id}[0])



//        println("entrou")
//        if (rpg.jogadores.size == 1){
//            rpg.jogadores.removeAt(0)
//        }else {
//            for (i in 0 until rpg.jogadores.size - 1) {
//                println("Entrasse")
//                println("i $i rpg.jogadores ${rpg.jogadores[i].id} esse id ${this.id}")
//                if (rpg.jogadores[i].id == this.id) {
//                    rpg.jogadores.removeAt(i)
//                    println("removido jogador com id ${this.id}")
//                    break
//                }
//            }
//        }
    }

    override fun derrota(rpg: Rpg): String {
        this.vida--
        this.dinheiro.times(((5..9).random()) / 10)

        var log = "[ :/ ] VOCE TEM ${this.vida} VIDAS RESTANTES\n"

        if (this.vida <= 0) {

            log += "[ :c ] VOCÊ MORREU\n"
            println("morreu")
            this.morrerJogador(rpg)

        } else {

            log += "[ :0 ] VOCÊ FOI OBLITERADO E RESTARAM ${this.dinheiro} MOEDAS DE OURO\n"

        }
        return log
    }

    fun vitoria(): String {
        this.dinheiro += (this.nivel * (1..3).random())
        val xpganho = 500 //-logx+2
        this.xp += xpganho

        var log =
            "[ $ ] AGORA VOCÊ ESTÁ COM ${this.dinheiro} MOEDAS DE OURO E GANHOU ${xpganho} XP NO NÍVEL ${this.nivel}\n"

        if (xpganho + this.xp >= 500 * this.nivel) {
            this.nivel += xpganho / 500
            this.Upnivel()
            log += "[ ↑ ] VOCÊ UPOU E AGORA ESTÁ NO NÍVEL ${this.nivel}\n"
        }

        if ((1..10).random() + this.sorte >= 9) {
            this.vida.plus(1)
            log += "[ ♥ ] VOCÊ ENCONTROU UMA POÇÃO DE VIDA NO MONSTRO, AGORA SUA VIDA É ${this.vida}\n"
        }

        if (this.sorte <= 2 && (1..100).random() == 1) {
            this.sorte.plus(1)
            log += "[ ♣ ] VOCÊ ENCONTROU UM TREVO DE QUATRO FOLHAS\n"
        }

        return log
    }

    fun Upnivel() {

        if (classe == 1) {
            if (elemento % 2 == 0) {
                this.ataque += (3 * this.nivel)
                this.defesa += (1 * this.nivel)
            } else {
                this.ataque += (2 * this.nivel)
                this.defesa += (2 * this.nivel)
            }

        } else {
            if (elemento % 2 == 0) {
                this.ataque += (2 * this.nivel)
                this.defesa += (2 * this.nivel)
            } else {
                this.ataque += (1 * this.nivel)
                this.defesa += (3 * this.nivel)
            }
        }

        if (vida < 5)
            this.vida++
    }
}