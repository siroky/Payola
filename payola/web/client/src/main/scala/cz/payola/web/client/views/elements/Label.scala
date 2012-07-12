package cz.payola.web.client.views.elements

import cz.payola.web.client.views.Component
import s2js.adapters.js.browser.document
import s2js.adapters.js.dom
import dom.Element

class Label(val text: String, val forElement: Element, val addClass: String = "") extends Component
{
    val label = document.createElement[dom.Anchor]("label")

    label.innerHTML = text
    label.setAttribute("for", forElement.getAttribute("id"))

    def render(parent: Element = document.body) = {
        parent.appendChild(label)
    }

    def getDomElement: Element = {
        label
    }
}