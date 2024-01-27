package org.visual.model.text.editor

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.layout.Region
import java.util.*

class GenericTextArea : Region() {
    private val READ_ONLY: PseudoClass = PseudoClass.getPseudoClass("readonly")
    private val HAS_CARET: PseudoClass = PseudoClass.getPseudoClass("has-caret")
    private val FIRST_PAR: PseudoClass = PseudoClass.getPseudoClass("first-paragraph")
    private val LAST_PAR: PseudoClass = PseudoClass.getPseudoClass("last-paragraph")
    private val editable: BooleanProperty = object : SimpleBooleanProperty(
        this, "editable", true
    ) {
        override fun invalidated() {
            (bean as Region).pseudoClassStateChanged(READ_ONLY, !get())
        }
    }
}