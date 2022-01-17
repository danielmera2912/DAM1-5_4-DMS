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
    fun obtenerAtributosEnMapKV(e: Element ):MutableMap<String, String>
    {
        val mMap = mutableMapOf<String, String>()
        for(j in 0..e.attributes.length - 1)
            mMap.putIfAbsent(e.attributes.item(j).nodeName, e.attributes.item(j).nodeValue)
        return mMap
    }
    fun obtenerNodosEnMapKV(e: Element ):MutableMap<String, String>
    {
        val mMap = mutableMapOf<String, String>()
        for(j in 0..e.childNodes.length - 1)
            mMap.putIfAbsent(e.childNodes.item(j).nodeName, e.childNodes.item(j).nodeValue)
        return mMap
    }

    fun obtenerListaNodosPorNombre( tagName: String): MutableList<Node>
    {
        val xmlDoc: Document= readXml()!!
        val bookList: NodeList = xmlDoc.getElementsByTagName(tagName)
        val lista = mutableListOf<Node>()
        for(i in 0..bookList.length - 1)
            lista.add(bookList.item(i))
        return lista
    }
    fun existeLibro(idLibro: String): Boolean {

        val libros = obtenerListaNodosPorNombre("book")
        var bol: Boolean= false
        var mMap:MutableMap<String, String> = mutableMapOf()
        libros.forEach{
            if (it.getNodeType() === Node.ELEMENT_NODE) {
                val elem = it as Element
                mMap = obtenerAtributosEnMapKV(it)

                if(mMap.containsValue(idLibro)){
                    bol=true
                }
                return bol
            }
        }
        return mMap.containsValue(idLibro)
    }

    fun infoLibro (idLibro:String): Map<String, Any>? {
        val libros = obtenerListaNodosPorNombre("book")
        var mMap:MutableMap<String, String> = mutableMapOf()
        var mAtributos:MutableMap<String, String> = mutableMapOf()
        var mNodos:MutableMap<String, String> = mutableMapOf()
        libros.forEach{
            if (it.getNodeType() === Node.ELEMENT_NODE) {
                val elem = it as Element
                mAtributos = obtenerAtributosEnMapKV(it)
                mNodos= obtenerNodosEnMapKV(it)
                if(mAtributos.containsValue(idLibro)){
                    return mAtributos+mNodos
                }

            }
        }
        return mMap
    }
}
fun main() {
    var ruta= "C:\\Users\\juper\\OneDrive\\Escritorio\\2DAM\\LC\\Tema5\\catalog.xml"
    var xmlDoc = CatalogoLibrosXML(ruta)
    xmlDoc.existeLibro("bk101")
    xmlDoc.infoLibro("bk101")

}