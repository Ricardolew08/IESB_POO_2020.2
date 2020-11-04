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

            post("/batalha/{idURL}") {

                //TODO menos baseada em sorte

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

                //TODO ajustar ao arquivo com os itens

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

            put("/taverna/chat/{idURL}/{texto}"){
                var msg = call.parameters["texto"].toString()
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                //Formatação da Data
                val data = LocalDateTime.now()
                val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
                val formatado = data.format(formato)

                if (jogador != null) {
                    msg = "<${formatado}> ${jogador.nome} diz: " + msg + "\n";
                    File("src/main/kotlin/br/iesb/poo/rpg/taverna/chat.txt").appendText(msg)
                    call.respond(HttpStatusCode.OK)
                } else{
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            get("/taverna/chat"){

                //TODO se estiver vazio

                call.respondText(File("src/main/kotlin/br/iesb/poo/rpg/taverna/chat.txt").readText())
            }

            //TODO delete(){}
        }
    }.start(wait = true)
}