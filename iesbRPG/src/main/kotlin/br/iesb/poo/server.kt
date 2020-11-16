package br.iesb.poo

import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.personagem.PersonagemJogador
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.iesb.poo.rpg.batalha.batalha
import br.iesb.poo.rpg.loja.Itens
import br.iesb.poo.rpg.taverna.Taverna
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val RPG: Rpg = Rpg()

fun main() {
    embeddedServer(Netty, 8080) {
        routing {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }
            }

            get("/") {
                call.respondText(
                    "<h1>Hello Kenniston</h1> </br> <h2>Programação Orientada a Objetos P1</h2>",
                    ContentType.Text.Html
                )
            }

            get("/jogadores") {
                if (RPG.jogadores.isNotEmpty()) {
                    call.respond(RPG.jogadores)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            get("/monstros") {
                if (RPG.monstros.isNotEmpty()) {
                    call.respond(RPG.monstros)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }



            post("/jogadores/criarjogador") {
                val atributos = call.receive<PersonagemJogador>()
                val novojogador = PersonagemJogador(
                    atributos.classe,
                    atributos.nome,
                    atributos.elemento,
                    RPG
                )
                RPG.jogadores.add(novojogador)
                call.respondText(
                    "Criado com sucesso ${if (novojogador.classe == 1) "Arqueiro" else "Cavaleiro"} ${novojogador.nome} de ID:${novojogador.id}",
                    status = HttpStatusCode.Created
                )
            }

            post("/batalha/{idURL}/{idajudante}") {

                //TODO menos baseada em sorte

                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador}
                val idAjudante = call.parameters["idajudante"]?.toInt()
                val ajudante = RPG.ajudante.find {it.id == idAjudante}
                if (jogador != null && idAjudante != null) {
                    val log: String = batalha(jogador, RPG, ajudante)
                    jogador.batalhas++
                    call.respondText(log)
                }else if (jogador != null && idAjudante == null) {
                    val log: String = batalha(jogador, RPG, ajudante)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            post("/loja/{idURL}/{opcao}") {

                //TODO ajustar ao arquivo com os itens

                val idJogador = call.parameters["idURL"]?.toInt()
                val opcao = call.parameters["opcao"].toString()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                if (jogador != null) {

                    val itens = Itens(opcao, "", "", "", -1, jogador)
                    val retorno = itens.buscar(opcao)

//                    var arrayteste = arrayListOf<String>()
//
//                    println(arrayteste[0])


                    if (!retorno.isNullOrEmpty()) {

                        if (jogador.dinheiro >= retorno[4].toInt()) {
                            jogador.dinheiro = jogador.dinheiro - retorno[4].toInt()

//                            var item = jogador.inventario.find{it[0] == opcao}
//                            println(item)
//                            if(item!= null){
//
//                                println("oi")
//                                var quantidade = itens.qtd++
//
//                                arrayteste.set(0,retorno[0])
//
//                                arrayteste.set(1,quantidade.toString())
//
//
//                            }else{
//                                println("oielse")
//                                arrayteste.set(0,retorno[0])
//                                arrayteste.set(1,"1")
//                            }

                            itens.efeito(jogador,opcao)

                            jogador.inventario.add(retorno)


//                            jogador.adicionarItem(jogador,retorno[0],0)
//                            val ite = (jogador.inventario.find{it[0].toString() == opcao}!![1])
//                            if(ite) {
//                                val qtdItem = itens.qtd + 1
//                                jogador.inventario.add(qtdItem.toString())
//                            }else{
//                                jogador.inventario.add(retorno[0].toInt(),"1")
//                            }


                            call.respondText(
                                "Você comprou ${retorno[2]} pelo valor de ${retorno[4]}! Muito Obrigada! Volte sempre",
                                status = (HttpStatusCode.OK)
                            )
                        } else {
                            call.respondText(
                                "Você não tem moedas de ouro suficientes para comprar ${retorno[2]}, E não vendemos fiado " +
                                        "Você possui um total de  ${jogador.dinheiro} moedas!",
                                status = (HttpStatusCode.Forbidden)
                            )

                        }

                    } else {
                        call.respondText(
                            "Infelizmente estamos com falta de estoque! Muito Obrigada! Agradeçemos a compreensão",
                            status = (HttpStatusCode.NoContent)
                        )

                    }
                } else {
                    call.respondText(
                        "Verifique o Id, jogador não exite!!",
                        status = (HttpStatusCode.NoContent)
                    )

                }
            }

            put("/jogadores/usaritem/{idURL}/{opcao}") {

            }

            post("/inventario/{idURL}") {

                //TODO menos baseada em sorte

                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }


                if (jogador != null) {
                    println("entrei3")

                    if (jogador.inventario.isNotEmpty()) {
                        println("entrei4")

                        call.respond(jogador.inventario)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }


//                val idJogador = call.parameters["idURL"]?.toInt()
//                val opcao = call.parameters["opcao"]?.toInt()
//                val jogador = RPG.jogadores.find { it.id == idJogador }
//
//                if (jogador != null) {
//                    val log: String = Loja(jogador, opcao)
//                    call.respondText(log)
//                } else {
//                    call.respond(HttpStatusCode.NoContent)
//                }
//            }

            post  ("/taverna/{idURL}" ){
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                if (jogador != null) {
                    if (jogador.dinheiro >= 50){
                        jogador.dinheiro = jogador.dinheiro - 50

                        val tav = Taverna()
                        var ajudante = tav.Algumacoisa(jogador, RPG)

                        jogador.ajudanteAtual.add(ajudante)

                        call.respondText(
                            "Você contratou pelo valor de 50! Muito Obrigada! Volte sempre! te restaram ${jogador.dinheiro} moedas de ouro",
                            status = (HttpStatusCode.OK)
                        )
                    }else{
                        call.respondText(
                            "Você não possui dinheiro o suficiente",
                            status = (HttpStatusCode.Forbidden)
                        )
                    }

                }
            }

            put("/taverna/chat/{idURL}/{texto}") {
                var msg = call.parameters["texto"].toString()
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                //Formatação da Data
                val data = LocalDateTime.now()
                val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
                val formatado = data.format(formato)

                if (jogador != null) {
                    msg = "<${formatado}> ${jogador.nome} diz: " + msg + "\n";
                    File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/taverna/chat.txt").appendText(msg)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            get("/taverna/chat") {

                //TODO se estiver vazio

                call.respondText(File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/taverna/chat.txt").readText())
            }

            //TODO delete(){}
        }
    }.start(wait = true)
}