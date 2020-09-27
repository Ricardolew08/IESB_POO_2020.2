package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemJogador(
    classeJogador: Int,
    nomeJogador: String,
    elementoJogador: Int,
    rpgAtual: Rpg
) : Personagem(nomeJogador, elementoJogador) {

    //Arqueiro = 1; Cavaleiro = 2
    //Arqueiro + Ataque; Cavaleiro + Defesa

    var classe: Int = classeJogador
    var sorte: Int = 0
    var vida: Int = 5
    private var xp: Int = 0

    init {
        id = genId(rpgAtual)
    }

    override fun genId(rpgAtual: Rpg): Int {
        var novaId = (0..10000).random()
        while (rpgAtual.jogadores.find{it.id == novaId} != null){
            novaId = (0..10000).random()
        }
        return novaId
    }


    private fun morrerJogador(rpg: Rpg): String {
        rpg.jogadores.remove(rpg.jogadores.filter { it.id == this.id }[0])
        return "[ ✝ ] VOCÊ MORREU, SEU PERSONAGEM FOI DELETADO\n"
    }

    override fun derrota(rpg: Rpg): String {
        this.vida--
        this.dinheiro.times(((5..9).random()) / 10)

        var log = "[ :c ] VOCE TEM ${this.vida} VIDAS RESTANTES\n"

        if (this.vida <= 0) {

            log += this.morrerJogador(rpg)

        } else {

            log += "[ :0 ] VOCÊ FOI OBLITERADO E RESTARAM ${this.dinheiro} MOEDAS DE OURO\n"

        }
        return log
    }

    fun vitoria(monstro: PersonagemMonstro): String {
        this.dinheiro += monstro.dinheiro
        val xpganho = monstro.nivel * 100
        var xpProxNv = 0
        var i = 0
        do {
            i++
            xpProxNv += i * 100
        }while (i in 1 until this.nivel)
        this.xp += xpganho
        var log =
            "[ $ ] AGORA VOCÊ ESTÁ COM ${this.dinheiro} MOEDAS DE OURO E GANHOU $xpganho XP NO NÍVEL ${this.nivel}\n"

        while (this.xp >= xpProxNv) {
            this.nivel++
            log += this.nivelUp()
            xpProxNv += this.nivel * 100
        }

        log += "XP PARA O PRÓXIMO NÍVEL: ${xpProxNv}\n"

        if ((1..10).random() + this.sorte >= 9) {
            this.vida.plus(1)
            log += "[ ♥ ] VOCÊ ENCONTROU UMA POÇÃO DE VIDA NO MONSTRO, AGORA SUA VIDA É ${this.vida}\n"
        }

        if (this.sorte <= 2 && (1..100).random() == 1) {
            this.sorte.plus(1)
            log += "[ ☘ ] VOCÊ ENCONTROU UM TREVO DE QUATRO FOLHAS\n"
        }

        return log
    }

    private fun nivelUp(): String {

        if (classe == 1) {
            if (elemento % 2 == 0) {
                this.ataque += (3)
                this.defesa += (1)
            } else {
                this.ataque += (2)
                this.defesa += (2)
            }
        } else {
            if (elemento % 2 == 0) {
                this.ataque += (2)
                this.defesa += (2)
            } else {
                this.ataque += (1)
                this.defesa += (3)
            }
        }

        var log = "[ ↑ ] VOCÊ UPOU E AGORA ESTÁ NO NÍVEL ${this.nivel}\n"
        if (vida < 5) {
            this.vida++
            log += "[ ♥ ] VOCÊ ESTAVA COM POUCA VIDA E ESSA BATALHA TE REVIGOROU, AGORA SUA VIDA É ${this.vida}\n"
        }
        return log
    }
}