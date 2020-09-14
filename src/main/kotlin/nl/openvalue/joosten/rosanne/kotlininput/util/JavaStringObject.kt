package nl.openvalue.joosten.rosanne.kotlininput.util

import java.io.IOException
import java.net.URI
import javax.tools.JavaFileObject

import javax.tools.SimpleJavaFileObject


internal class JavaStringObject constructor(name: String, private val source: String) : SimpleJavaFileObject(URI.create("string:///" + name.replace("\\.".toRegex(), "/") +
        JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE) {
    @Throws(IOException::class)
    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence {
        return source
    }

}
