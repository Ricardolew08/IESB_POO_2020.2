package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg
import kotlin.text.toInt as toInt1

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
    //var inventario = mutableListOf<ArrayList<String>>()

    val inventario = arrayOf<Array<String>>() //[Item,Quantidade]
    //var inventario = [[PotP, X], [PotM, y]...]
    //equipados = [arma,armadura]

    init {
        id = genId(rpgAtual)

    }

    override fun genId(rpgAtual: Rpg): Int {

        var novaId = (0..10000).random()
        while (rpgAtual.jogadores.find { it.id == novaId } != null) { //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    private fun morrerJogador(rpg: Rpg): String {

        rpg.jogadores.remove(rpg.jogadores.find { it.id == this.id })
        return "[ ✝ ] VOCÊ MORREU, SEU PERSONAGEM FOI DELETADO\n"
    }

    override fun derrota(rpg: Rpg): String {

        this.vida--
        this.dinheiro.times(((5..9).random()) / 10)

        var log = "[ :c ] VOCE TEM ${this.vida} VIDAS RESTANTES\n"

        log += if (this.vida <= 0) {

            this.morrerJogador(rpg)

        } else {

            "[ :0 ] VOCÊ FOI OBLITERADO E RESTARAM ${this.dinheiro} MOEDAS DE OURO\n"

        }
        return log
    }

    fun vitoria(monstro: PersonagemMonstro): String { //CALCULAR RECOMPENSAS DE VITÓRIA NO COMBATE E ATUALIZAR NÍVEL

        this.dinheiro += monstro.dinheiro
        val xpganho = monstro.nivel * 100
        this.xp += xpganho

        var log =
            "[ $ ] AGORA VOCÊ ESTÁ COM ${this.dinheiro} MOEDAS DE OURO E GANHOU $xpganho XP NO NÍVEL ${this.nivel}\n"

        var xpProxNv = 0
        var i = 0

        // LOOP PARA CALCULAR XP NECESSÁRIO PARA O PRÓXIMO NÍVEL
        do {
            i++
            xpProxNv += i * 100
        } while (i in 1 until this.nivel)

        // LOOP PARA ATUALIZAR NÍVEL DO PERSONAGEM CASO GANHE XP SUFICIENTE PARA MAIS DE UMA EVOLUÇÃO
        while (this.xp >= xpProxNv) {
            this.nivel++
            log += this.nivelUp()
            xpProxNv += this.nivel * 100
        }

        log += "[ ➽ ] XP ATUAL: ${this.xp}\n"
        log += "[ ➽ ] XP NECESSÁRIO PARA O PRÓXIMO NÍVEL: ${xpProxNv}\n"

        if ((1..10).random() + this.sorte >= 9) {
                this.vida++
                log += "[ ♥ ] VOCÊ ENCONTROU UMA POÇÃO DE VIDA NOS ESPÓLIOS, AGORA SUA VIDA É ${this.vida}\n"
        }

        if (this.sorte <= 2 && (1..100).random() == 1) {
            this.sorte++
            log += "[ ☘ ] VOCÊ ENCONTROU UM TREVO DE QUATRO FOLHAS E JÁ SE SENTE MAIS SORTUDO\n"
        }

        return log
    }

    private fun nivelUp(): String { //ATUALIZAR ATAQUE/DEFESA EM RELAÇÃO AO ELEMENTO ATUAL QUANDO AUMENTAR NÍVEL

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

            log += "[ ♥ ] JUNTO COM A EXPERIÊNCIA ADQUIRIDA VOCÊ SE SENTE REVIGORADO, SUA VIDA AGORA É ${this.vida}\n"

        }
        return log
    }

    open fun adicionarItem(jogador : PersonagemJogador ,id: String, qtd : Int){
        (jogador.inventario.find{it[0] == id}!![1])
    }
}