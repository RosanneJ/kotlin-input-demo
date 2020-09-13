package nl.openvalue.joosten.rosanne.kotlininput.controller

import nl.openvalue.joosten.rosanne.kotlininput.data.InputEntity
import nl.openvalue.joosten.rosanne.kotlininput.repository.InputRepository
import nl.openvalue.joosten.rosanne.kotlininput.util.CompileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.Pattern.DOTALL

@RestController
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
class KotlinInputRestController {
    @Autowired
    lateinit var repository: InputRepository

    @Autowired
    lateinit var compileService: CompileService

    @GetMapping("/greeting", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun greeting(@RequestParam(value = "name", defaultValue = "Anonymous") name: String): Greeting {
        return when {
            name != "Anonymous" -> {
                repository.save(InputEntity(name = name))
                 Greeting("Nice to meet you, $name", true)
            }
            else -> {
                repository.save(InputEntity(name = name))
                Greeting("You will stay Anonymous", true)
            }
        }
    }

    @PostMapping("/kotlin-input")
    fun kotlinInput(@RequestParam(value = "name", defaultValue = "Anonymous") name: String, @RequestParam(value = "problem") problemNo: Int, @RequestBody text: String): ProblemAnalysis {
        val p: Pattern = Pattern.compile(".*\\s?class\\s(\\w+)\\s?\\{.*", DOTALL)
        val m: Matcher = p.matcher(text)
        val matches: Boolean = m.matches()
        if(!matches) {
            return ProblemAnalysis(false, emptyList(), "Wrap your code in a class with main method")
        }
        val className = m.group(1)
        return compileService.compileJavaCode(className, text)
    }

}


data class Greeting(val content: String, val newUser: Boolean)
data class ProblemAnalysis(val success: Boolean, val invalidPositions: List<Long>, val hint: String)
