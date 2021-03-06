package io.github.zwieback.familyfinance.business.operation.fragment

import io.github.zwieback.familyfinance.business.operation.adapter.IncomeOperationAdapter
import io.github.zwieback.familyfinance.business.operation.filter.IncomeOperationFilter
import io.github.zwieback.familyfinance.business.operation.filter.IncomeOperationFilter.Companion.INCOME_OPERATION_FILTER

class IncomeOperationFragment : OperationFragment<IncomeOperationFilter>() {

    override val filterName: String
        get() = INCOME_OPERATION_FILTER

    override fun createEntityAdapter(): IncomeOperationAdapter {
        val filter = extractFilter()
        return IncomeOperationAdapter(requireContext(), clickListener, data, filter)
    }

    companion object {
        fun newInstance(filter: IncomeOperationFilter) = IncomeOperationFragment().apply {
            arguments = createArguments(INCOME_OPERATION_FILTER, filter)
        }
    }
}
