package nl.openvalue.joosten.rosanne.kotlininput.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URI
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject


internal class JavaByteObject constructor(name: String) : SimpleJavaFileObject(URI.create("bytes:///" + name + name.replace("\\.".toRegex(), "/")), JavaFileObject.Kind.CLASS) {
    private val outputStream: ByteArrayOutputStream

    //overriding this to provide our OutputStream to which the
    // bytecode can be written.
    @Throws(IOException::class)
    override fun openOutputStream(): OutputStream {
        return outputStream
    }

    val bytes: ByteArray
        get() = outputStream.toByteArray()

    init {
        outputStream = ByteArrayOutputStream()
    }
}
