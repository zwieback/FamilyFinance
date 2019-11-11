package io.github.zwieback.familyfinance.business.currency.lifecycle.creator

import io.github.zwieback.familyfinance.core.lifecycle.creator.EntityViewCreator
import java.sql.Connection

class CurrencyViewCreator(connection: Connection) : EntityViewCreator(connection) {

    override val viewName: String
        get() = "v_currency"

    override val viewBody: String
        get() = " SELECT cu.id         AS id," +
                "       cu.icon_name   AS icon_name," +
                "       cu.name        AS name," +
                "       cu.description AS description" +
                "  FROM currency cu"
}