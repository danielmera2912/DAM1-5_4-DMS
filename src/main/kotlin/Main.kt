import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


class CatalogoLibrosXML() {
    var cargador: String = ""

    constructor(cargador: String) : this() {
        this.cargador = cargador
    }

    fun readXml(): Document? {
        try{
            val xmlFile = File(this.cargador)
            val xmlDoc: Document=  DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile)
            return xmlDoc
        }catch (e: Exception){
            return null
        }

    }

    fun existeLibro(idLibro: String): Boolean {
        val xmlDoc: Document= readXml()!!
        xmlDoc.documentElement.normalize()
        val libros: NodeList = xmlDoc.getElementsByTagName("id")
        var bol: Boolean= false
        for (i in 0..libros.length - 1) {
            var bookNode: Node= libros.item(i)
            if (bookNode.toString() == idLibro) {
                bol=true
            }
        }
        return bol
    }

    fun infoLibro (idLibro:String): Map<String, Any>? {
        val mMap = mutableMapOf<String, Any>()
        val xmlDoc: Document= readXml()!!
        xmlDoc.documentElement.normalize()
        val libros: NodeList = xmlDoc.getElementsByTagName("id")
        for (i in 0..libros.length - 1) {
            var bookNode: Node= libros.item(i)
            if (bookNode.getNodeValue() == idLibro) {
                var e: Node = libros.item(i)

                for(j in 0..e.attributes.length - 1)
                    mMap.putIfAbsent(e.attributes.item(j).nodeName, e.attributes.item(j).nodeValue)

            }
        }
        return mMap
    }
}
fun main() {
    var ruta= "C:\\Users\\juper\\OneDrive\\Escritorio\\2DAM\\LC\\Tema5\\catalog.xml"
    var xmlDoc = CatalogoLibrosXML(ruta)
    xmlDoc.existeLibro("bk101")


}