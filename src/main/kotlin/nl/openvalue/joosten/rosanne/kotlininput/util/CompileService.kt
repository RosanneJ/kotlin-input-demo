package nl.openvalue.joosten.rosanne.kotlininput.util

import nl.openvalue.joosten.rosanne.kotlininput.controller.ProblemAnalysis
import nl.openvalue.joosten.rosanne.kotlininput.data.JavaSourceFromString
import org.springframework.stereotype.Component
import java.util.*
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.ToolProvider
import kotlin.collections.ArrayList

@Component
class CompileService {

    fun compileJavaCode(className: String, text: String): ProblemAnalysis {
        val file = JavaSourceFromString(className, text)
        val compiler = ToolProvider.getSystemJavaCompiler()
        val diagnostics = DiagnosticCollector<JavaFileObject>()

        val compilationUnits = Arrays.asList(file)
        val task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits)

        val success = task.call()
        val invalidPositions = ArrayList<Long>()
        for (diagnostic in diagnostics.getDiagnostics()) {
            invalidPositions.add(diagnostic.position);
            System.out.println(diagnostic.getCode())
            System.out.println(diagnostic.getKind())
            System.out.println(diagnostic.getPosition())
            System.out.println(diagnostic.getStartPosition())
            System.out.println(diagnostic.getEndPosition())
            System.out.println(diagnostic.getSource())
            System.out.println(diagnostic.getMessage(null))
        }
        return ProblemAnalysis(success, invalidPositions, "")
    }
}
