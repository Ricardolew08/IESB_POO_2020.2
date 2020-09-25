package br.iesb.poo
//Import no build.gradle
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
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

data class nomejogador(var data:String)

//post("/batalha"){}
val RPG : Rpg = Rpg()


fun main() {



    embeddedServer(Netty, 8080) {
        routing {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }
            }
            get("/") {
                call.respondText("<h1>Hello Kenniston !!!! </h1> </br> <h2>Programação Orientada a Objetos P1</h2> ", ContentType.Text.Html)
            }
            get("/jogadores"){
                call.respond(RPG.jogadores)
            }
            get("/monstros"){
                call.respond(RPG.monstros)
            }
            // check disponibilidade do nome do jogador
            post("/jogadores/criarjogador"){
                val novojogador = call.receive<PersonagemJogador>()
                println(novojogador.classe)
                println(novojogador.nivel)
                println(novojogador.ataque)
                println(novojogador.defesa)
                RPG.jogadores.add(PersonagemJogador(novojogador.classe, nick = (novojogador.nome as String), novojogador.elemento))
            }
            post("/batalha/{nomejogador}"){
                val nomejogador = call.parameters["nomejogador"]
                println(nomejogador)
                var retorno : String = batalha(RPG.jogadores.filter{it.nome==nomejogador}[0], RPG)
                call.respondText(retorno)

            }








        }
    }.start(wait = true)

}