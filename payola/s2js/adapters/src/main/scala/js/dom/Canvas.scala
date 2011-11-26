package s2js.adapters.js.dom

abstract class Canvas extends Element {
    var height = 0

    var width = 0

    def getContext(contextId: String): CanvasContext
}