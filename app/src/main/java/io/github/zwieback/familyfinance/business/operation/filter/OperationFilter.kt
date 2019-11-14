package io.github.zwieback.familyfinance.business.operation.filter

import android.os.Parcel
import io.github.zwieback.familyfinance.core.filter.EntityFilter
import io.github.zwieback.familyfinance.util.DateUtils
import io.github.zwieback.familyfinance.util.DateUtils.readLocalDateFromParcel
import io.github.zwieback.familyfinance.util.DateUtils.writeLocalDateToParcel
import io.github.zwieback.familyfinance.util.NumberUtils.ID_AS_NULL
import io.github.zwieback.familyfinance.util.NumberUtils.intToIntegerId
import io.github.zwieback.familyfinance.util.NumberUtils.integerToIntId
import io.github.zwieback.familyfinance.util.NumberUtils.readBigDecimalFromParcel
import io.github.zwieback.familyfinance.util.NumberUtils.writeBigDecimalToParcel
import org.threeten.bp.LocalDate
import java.math.BigDecimal

abstract class OperationFilter : EntityFilter {

    lateinit var startDate: LocalDate
    lateinit var endDate: LocalDate
    var startValue: BigDecimal? = null
    var endValue: BigDecimal? = null
    private var articleId: Int = 0
    private var accountId: Int = 0
    private var ownerId: Int = 0
    private var currencyId: Int = 0

    constructor() : super()

    constructor(filter: OperationFilter) : super(filter) {
        startDate = filter.startDate
        endDate = filter.endDate
        startValue = filter.startValue
        endValue = filter.endValue
        setArticleId(filter.getArticleId())
        setAccountId(filter.getAccountId())
        setOwnerId(filter.getOwnerId())
        setCurrencyId(filter.getCurrencyId())
    }

    internal constructor(`in`: Parcel) : super(`in`)

    override fun init() {
        super.init()
        startDate = DateUtils.startOfMonth()
        endDate = DateUtils.endOfMonth()
        articleId = ID_AS_NULL
        accountId = ID_AS_NULL
        ownerId = ID_AS_NULL
        currencyId = ID_AS_NULL
    }

    override fun readFromParcel(`in`: Parcel) {
        startDate = readLocalDateFromParcel(`in`) ?: error("Can't read startDate from parcel")
        endDate = readLocalDateFromParcel(`in`) ?: error("Can't read endDate from parcel")
        startValue = readBigDecimalFromParcel(`in`)
        endValue = readBigDecimalFromParcel(`in`)
        articleId = `in`.readInt()
        accountId = `in`.readInt()
        ownerId = `in`.readInt()
        currencyId = `in`.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        writeLocalDateToParcel(out, startDate)
        writeLocalDateToParcel(out, endDate)
        writeBigDecimalToParcel(out, startValue)
        writeBigDecimalToParcel(out, endValue)
        out.writeInt(articleId)
        out.writeInt(accountId)
        out.writeInt(ownerId)
        out.writeInt(currencyId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OperationFilter

        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (startValue != other.startValue) return false
        if (endValue != other.endValue) return false
        if (articleId != other.articleId) return false
        if (accountId != other.accountId) return false
        if (ownerId != other.ownerId) return false
        if (currencyId != other.currencyId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startDate.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + (startValue?.hashCode() ?: 0)
        result = 31 * result + (endValue?.hashCode() ?: 0)
        result = 31 * result + articleId
        result = 31 * result + accountId
        result = 31 * result + ownerId
        result = 31 * result + currencyId
        return result
    }

    fun getArticleId(): Int? {
        return intToIntegerId(articleId)
    }

    fun setArticleId(articleId: Int?) {
        this.articleId = integerToIntId(articleId)
    }

    fun getAccountId(): Int? {
        return intToIntegerId(accountId)
    }

    fun setAccountId(accountId: Int?) {
        this.accountId = integerToIntId(accountId)
    }

    fun getOwnerId(): Int? {
        return intToIntegerId(ownerId)
    }

    fun setOwnerId(ownerId: Int?) {
        this.ownerId = integerToIntId(ownerId)
    }

    fun getCurrencyId(): Int? {
        return intToIntegerId(currencyId)
    }

    fun setCurrencyId(currencyId: Int?) {
        this.currencyId = integerToIntId(currencyId)
    }
}