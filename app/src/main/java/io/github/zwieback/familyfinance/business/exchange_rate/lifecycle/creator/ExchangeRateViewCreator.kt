package io.github.zwieback.familyfinance.business.exchange_rate.lifecycle.creator

import io.github.zwieback.familyfinance.core.lifecycle.creator.EntityViewCreator
import java.sql.Connection

class ExchangeRateViewCreator(connection: Connection) : EntityViewCreator(connection) {

    override val viewName: String
        get() = "v_exchange_rate"

    override val viewBody: String
        get() = " SELECT er.id       AS id," +
                "       er.icon_name AS icon_name," +
                "       er._value    AS _value," +
                "       er._date     AS _date," +
                "       cu.id        AS currency_id," +
                "       cu.name      AS currency_name" +
                "  FROM exchange_rate er" +
                "       INNER JOIN currency cu ON cu.id = er.currency_id"
}