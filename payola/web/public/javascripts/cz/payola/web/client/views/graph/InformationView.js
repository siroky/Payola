goog.provide('cz.payola.web.client.views.graph.InformationView');
goog.require('cz.payola.web.client.views.Constants');
goog.require('cz.payola.web.client.views.graph.View');
goog.require('scala.IndexOutOfBoundsException');
goog.require('scala.None');
goog.require('scala.Product');
goog.require('scala.Some');
goog.require('scala.runtime.ScalaRunTime');
cz.payola.web.client.views.graph.InformationView = function(modelObject) {
var self = this;
self.modelObject = modelObject;
goog.base(self);goog.object.extend(self, new scala.Product());
};
goog.inherits(cz.payola.web.client.views.graph.InformationView, cz.payola.web.client.views.graph.View);
cz.payola.web.client.views.graph.InformationView.prototype.draw = function(context, color, position) {
var self = this;
context.fillStyle = (function() {
if ((color != null)) {
return color.toString();
} else {
return cz.payola.web.client.views.Constants.ColorText.toString();
}})();
context.font = '12px Sans';
context.textAlign = 'center';
context.fillText(self.modelObject.uri, position.x, position.y);
};
cz.payola.web.client.views.graph.InformationView.prototype.copy = function(modelObject) {
var self = this;
if (typeof(modelObject) === 'undefined') { modelObject = self.modelObject; }
return new cz.payola.web.client.views.graph.InformationView(modelObject);
};
cz.payola.web.client.views.graph.InformationView.prototype.toString = function() {
var self = this;
return scala.runtime.ScalaRunTime._toString(self);
};
cz.payola.web.client.views.graph.InformationView.prototype.productPrefix = function() {
var self = this;
return 'InformationView';
};
cz.payola.web.client.views.graph.InformationView.prototype.productArity = function() {
var self = this;
return 1;
};
cz.payola.web.client.views.graph.InformationView.prototype.productElement = function($x$1) {
var self = this;
return (function($selector_1) {
if ($selector_1 === 0) {
return cz.payola.web.client.views.graph.InformationView.modelObject;
}
if (true) {
return (function() {
throw new scala.IndexOutOfBoundsException($x$1.toString());
})();
}
})($x$1);
};
cz.payola.web.client.views.graph.InformationView.prototype.metaClass_ = new s2js.MetaClass('cz.payola.web.client.views.graph.InformationView', [cz.payola.web.client.views.graph.View, scala.Product]);
cz.payola.web.client.views.graph.InformationView.toString = function() {
var self = this;
return 'InformationView';
};
cz.payola.web.client.views.graph.InformationView.unapply = function(x$0) {
var self = this;
return (function() {
if ((x$0 == null)) {
return scala.None;
} else {
return new scala.Some(x$0.modelObject);
}})();
};
cz.payola.web.client.views.graph.InformationView.$apply = function(modelObject) {
var self = this;
return new cz.payola.web.client.views.graph.InformationView(modelObject);
};
cz.payola.web.client.views.graph.InformationView.metaClass_ = new s2js.MetaClass('cz.payola.web.client.views.graph.InformationView', []);
