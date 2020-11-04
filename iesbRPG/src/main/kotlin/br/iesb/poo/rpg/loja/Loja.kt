package br.iesb.poo.rpg.loja


import br.iesb.poo.rpg.personagem.PersonagemJogador

fun Loja (jogadorentrada: PersonagemJogador,
          opcao: Int?
):  String{

    val log = compra(jogadorentrada,opcao)

    return log

}
fun compra(jogador: PersonagemJogador, op: Int?): String {
    var log = ""
    if (op == 1) {
        if (jogador.dinheiro >= 10) {
            jogador.dinheiro -= 10
            jogador.vida++
            log+="VOCÊ COMPROU UMA POÇÃO DE VIDA, APROVEITE!"

        }else{
            log+="VOCÊ NÃO POSSUI MOEDAS SUFICIENTES"
        }
    }
    if (op == 2) {

        if (jogador.dinheiro >= 10) {
            jogador.dinheiro -= 10
            jogador.ataque++

            log+="UAU VOCÊ AFIOU A SUA ESPADA, APROVEITE ESSA MELHORIA NO ATAQUE"
        }else{
            log+="VOCÊ NÃO POSSUI MOEDAS SUFICIENTES"

        }
    }
    if(op == 3){
        if (jogador.dinheiro >= 10) {
            jogador.dinheiro -= 10
            jogador.defesa++
            log+="UAU COMO SUA ARMADURA ESTÁ RELUZENTE, APROVEITE ESSA MELHORIA NA DEFESA"

        }else{
            log+="VOCÊ NÃO POSSUI MOEDAS SUFICIENTES"

        }
    }

    return log
}



