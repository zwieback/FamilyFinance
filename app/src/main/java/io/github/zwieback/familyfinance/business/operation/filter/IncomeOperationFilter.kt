package io.github.zwieback.familyfinance.business.operation.filter

import io.github.zwieback.familyfinance.constant.IdConstants.EMPTY_ID
import io.github.zwieback.familyfinance.extension.BigDecimalParceler
import io.github.zwieback.familyfinance.extension.LocalDateParceler
import io.github.zwieback.familyfinance.extension.endOfMonth
import io.github.zwieback.familyfinance.extension.startOfMonth
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.threeten.bp.LocalDate
import java.math.BigDecimal

@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>
@TypeParceler<BigDecimal?, BigDecimalParceler>
data class IncomeOperationFilter(
    override var startDate: LocalDate = LocalDate.now().startOfMonth(),
    override var endDate: LocalDate = LocalDate.now().endOfMonth(),
    override var startValue: BigDecimal? = null,
    override var endValue: BigDecimal? = null,
    override var articleId: Int = EMPTY_ID,
    override var accountId: Int = EMPTY_ID,
    override var toWhomIsNull: Boolean = false,
    override var ownerId: Int = EMPTY_ID,
    override var toWhomId: Int = EMPTY_ID,
    override var currencyId: Int = EMPTY_ID
) : OperationFilter() {

    companion object {
        const val INCOME_OPERATION_FILTER = "incomeOperationFilter"
    }
}
