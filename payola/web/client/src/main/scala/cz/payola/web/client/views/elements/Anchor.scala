package cz.payola.web.client.views.elements

import s2js.adapters.js.html
import cz.payola.web.client.views._
import cz.payola.web.client.View
import s2js.adapters.html.elements.Anchor

class Anchor(subViews: Seq[View] = Nil, href: String = "#", cssClass: String = "")
    extends ElementView[html.elements.Anchor]("a", subViews, cssClass)
{
    setAttribute("href", href)
}
