package cz.payola.model.parameter

import cz.payola._
import model.generic.ConcreteNamedEntity

abstract class Parameter[A](n: String, private val defaultValue: A) extends common.model.Parameter[A]
    with ConcreteNamedEntity
{
    setName(n)

    /** Creates a new instance of the particular parameter with value @value or
      * defaultValue if value is empty or null
      *
      * @param value Value. Can be omitted, then default value is used.
      * @return Instance of this parameter.
      */
    def createInstance(value: Option[A] = null) = {
        if (value == null || value.isEmpty)
            instanceWithValue(defaultValue)
        else
            instanceWithValue(value.get)
    }

    /** Returns a new ParameterInstance instance (of its subclass, to be precise) with the value passed
      * as a parameter of this method.
      *
      * @param value The value.
      *
      * @return New ParameterInstance instance.
      */
    protected def instanceWithValue(value: A): ParameterInstance[A]
}
