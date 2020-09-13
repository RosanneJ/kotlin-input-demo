package nl.openvalue.joosten.rosanne.kotlininput.controller

import nl.openvalue.joosten.rosanne.kotlininput.data.InputEntity
import nl.openvalue.joosten.rosanne.kotlininput.data.JavaSourceFromString
import nl.openvalue.joosten.rosanne.kotlininput.repository.InputRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.Pattern.DOTALL
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.ToolProvider

@RestController
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
class KotlinInputRestController {
    @Autowired
    lateinit var repository: InputRepository

    @GetMapping("/greeting", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun greeting(@RequestParam(value = "name", defaultValue = "Anonymous") name: String): Greeting {
        return when {
            nameExists(name) -> Greeting("Welcome back $name", false)
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
        m.matches()
        val retrieveClass = m.group(1)
        val file = JavaSourceFromString(retrieveClass, text)
        val compiler = ToolProvider.getSystemJavaCompiler()
        val diagnostics = DiagnosticCollector<JavaFileObject>()

        val compilationUnits = Arrays.asList(file)
        val task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits)

        val success = task.call()
        for (diagnostic in diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getCode())
            System.out.println(diagnostic.getKind())
            System.out.println(diagnostic.getPosition())
            System.out.println(diagnostic.getStartPosition())
            System.out.println(diagnostic.getEndPosition())
            System.out.println(diagnostic.getSource())
            System.out.println(diagnostic.getMessage(null))
        }
        return ProblemAnalysis(success)
    }

    fun nameExists(name: String): Boolean {
        val inputEntity = repository.findInputEntityByName(name)
        return inputEntity != null && inputEntity.yearsOfExperience != -1L
    }

}


data class Greeting(val content: String, val newUser: Boolean)
data class ProblemAnalysis(val success: Boolean)
