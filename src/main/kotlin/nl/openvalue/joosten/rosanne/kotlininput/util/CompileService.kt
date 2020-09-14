package nl.openvalue.joosten.rosanne.kotlininput.util

import nl.openvalue.joosten.rosanne.kotlininput.controller.ProblemAnalysis
import org.springframework.stereotype.Component
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import javax.tools.*
import kotlin.collections.ArrayList


@Component
class CompileService {

    fun compileJavaCode(className: String, text: String): ProblemAnalysis {
        val compiler = ToolProvider.getSystemJavaCompiler()
        val diagnostics = DiagnosticCollector<JavaFileObject>()
        val byteObject = JavaByteObject(className)

        val standardFileManager: StandardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null)
        val fileManager: JavaFileManager = createFileManager(standardFileManager, byteObject)

        val task = compiler.getTask(null,
                fileManager, diagnostics, null, null, getCompilationUnits(className, text))

        val inMemoryClassLoader: ClassLoader = createClassLoader(byteObject);


        val success = task.call()
        val invalidPositions = ArrayList<Long>()
        for (diagnostic in diagnostics.diagnostics) {
            invalidPositions.add(diagnostic.position);
            println(diagnostic.code)
            println(diagnostic.kind)
            println(diagnostic.position)
            println(diagnostic.startPosition)
            println(diagnostic.endPosition)
            println(diagnostic.source)
            println(diagnostic.getMessage(null))
        }

        if (success) {
            try {
                val mainMethod = Class.forName(className, true, inMemoryClassLoader).getDeclaredMethod("main", Array<String>::class.java)
                mainMethod.invoke(null, null)
            } catch (e: ClassNotFoundException) {
                System.err.println("Class not found: $e")
            } catch (e: NoSuchMethodException) {
                System.err.println("No such method: $e")
            } catch (e: IllegalAccessException) {
                System.err.println("Illegal access: $e")
            } catch (e: InvocationTargetException) {
                System.err.println("Invocation target: $e")
            }
        }

        return ProblemAnalysis(success, invalidPositions, "")
    }

    fun getCompilationUnits(className: String, source: String): Iterable<JavaFileObject> {
        val stringObject = JavaStringObject(className, source)
        return listOf(stringObject)
    }


    private fun createFileManager(fileManager: StandardJavaFileManager,
                                  byteObject: JavaByteObject): JavaFileManager {
        return object : ForwardingJavaFileManager<StandardJavaFileManager?>(fileManager) {
            @Throws(IOException::class)
            override fun getJavaFileForOutput(location: JavaFileManager.Location,
                                              className: String, kind: JavaFileObject.Kind,
                                              sibling: FileObject): JavaFileObject {
                return byteObject
            }
        }
    }

    private fun createClassLoader(byteObject: JavaByteObject): ClassLoader {
        return object : ClassLoader() {
            @Throws(ClassNotFoundException::class)
            override fun findClass(name: String): Class<*>? {
                //no need to search class path, we already have byte code.
                val bytes = byteObject.bytes
                return defineClass(name, bytes, 0, bytes.size)
            }
        }
    }
}
