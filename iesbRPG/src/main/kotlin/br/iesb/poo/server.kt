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
import br.iesb.poo.rpg.loja.Loja

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

            //POST?
            post("/batalha/{idURL}") {
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }
                if (jogador != null) {
                    val log: String = batalha(jogador, RPG)
                    call.respondText(log)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            put("/loja_do_seu_jorge/{idURL}/{opcao}") {
                val idJogador = call.parameters["idURL"]?.toInt()
                val opcao = call.parameters["opcao"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                if (jogador != null) {
                    val log: String = Loja(jogador, opcao)
                    call.respondText(log)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }
            //TODO delete(){}
        }
    }.start(wait = true)
}