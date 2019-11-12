package io.github.zwieback.familyfinance.business.template.lifecycle.destroyer

import io.github.zwieback.familyfinance.core.lifecycle.destroyer.EntityViewDestroyer
import java.sql.Connection

class TemplateViewDestroyer(connection: Connection) : EntityViewDestroyer(connection) {

    override val viewName: String
        get() = "v_template"
}