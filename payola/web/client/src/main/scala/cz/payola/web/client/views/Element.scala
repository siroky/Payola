package cz.payola.web.client.views

import scala.collection._
import s2js.adapters.js.dom
import s2js.adapters.js.browser.document
import cz.payola.web.client.views.events.BrowserEvent

abstract class Element[A <: dom.Element](domElementName: String, val innerComponents: Seq[Component], cssClass: String)
    extends Component
{
    val domElement = document.createElement[A](domElementName)

    val keyPressed = new BrowserEvent[this.type]

    val keyReleased = new BrowserEvent[this.type]

    val mouseClicked = new BrowserEvent[this.type]

    val mouseDoubleClicked = new BrowserEvent[this.type]

    val mousePressed = new BrowserEvent[this.type]

    val mouseReleased = new BrowserEvent[this.type]

    val mouseMoved = new BrowserEvent[this.type]

    val mouseWheelRotated = new BrowserEvent[this.type]

    protected var parentNode: Option[dom.Node] = None

    domElement.onkeyup = { e => keyPressed.triggerDirectly(this, e) }
    domElement.onkeydown = { e => keyReleased.triggerDirectly(this, e) }
    domElement.onclick = { e => mouseClicked.triggerDirectly(this, e) }
    domElement.ondblclick = { e => mouseDoubleClicked.triggerDirectly(this, e) }
    domElement.onmousedown = { e => mousePressed.triggerDirectly(this, e) }
    domElement.onmouseup = { e => mouseReleased.triggerDirectly(this, e) }
    domElement.onmousemove = { e => mouseMoved.triggerDirectly(this, e) }
    domElement.onmousewheel = { e => mouseWheelRotated.triggerDirectly(this, e) }
    addCssClass(cssClass)

    def render(parent: dom.Node) {
        parentNode = Some(parent)
        parent.appendChild(domElement)
        innerComponents.foreach(_.render(domElement))
    }

    def destroy() {
        parentNode.foreach(_.removeChild(domElement))
    }

    def getAttribute(name: String): String = {
        domElement.getAttribute(name)
    }

    def setAttribute(name: String, value: String) {
        domElement.setAttribute(name, value)
    }

    def addCssClass(cssClass: String) {
        removeCssClass(cssClass)
        setAttribute("class", getAttribute("class") + " " + cssClass)
    }

    def removeCssClass(cssClass: String) {
        setAttribute("class", getAttribute("class").replaceAllLiterally(cssClass, ""))
    }

    def id: String = {
        getAttribute("id")
    }

    def id_=(value: String) {
        setAttribute("id", value)
    }
}