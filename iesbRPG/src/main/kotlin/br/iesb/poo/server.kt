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
                    "<h1>Hello Kenniston</h1> </br> <h2>Programação Orientada a Objetos P1</h2> ",
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
            // check disponibilidade do nome do jogador
            post("/jogadores/criarjogador") {
                val novojogador = call.receive<PersonagemJogador>()
                RPG.jogadores.add(
                    PersonagemJogador(
                        novojogador.classe,
                        (novojogador.nome),
                        novojogador.elemento,
                        RPG.contadorJ++
                    )
                )
                call.respondText(
                    "Criado com sucesso ${novojogador.nome} de ID:${RPG.contadorJ - 1}",
                    status = HttpStatusCode.Created
                )
            }

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

            put("/loja/{idURL}") {
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }
                if (jogador != null) {
                    println("AAAAA")
                    if (jogador.dinheiro >= 10) {
                        jogador.dinheiro -= 10
                        jogador.vida++
                        call.respondText(
                            "${jogador.nome} comprou uma poção de vida e gastou 10 moedas, agora você possui ${jogador.dinheiro} moedas de ouro e ${jogador.vida} vidas.",
                            status = HttpStatusCode.Accepted
                        )
                    } else {
                        call.respondText(
                            "${jogador.nome} não possui 10 moedas de ouro para comprar uma poção de vida.",
                            status = HttpStatusCode.Forbidden
                        )
                    }
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }
            /*
            delete()
            {
            */
        }
    }.start(wait = true)
}