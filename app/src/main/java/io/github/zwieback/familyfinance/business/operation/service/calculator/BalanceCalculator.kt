package io.github.zwieback.familyfinance.business.operation.service.calculator

import io.github.zwieback.familyfinance.core.model.OperationView
import io.github.zwieback.familyfinance.core.model.type.OperationType
import io.github.zwieback.familyfinance.util.NumberUtils.bigDecimalToString
import io.requery.query.Result
import java.math.BigDecimal

object BalanceCalculator {

    fun calculateBalance(queryResult: Result<OperationView>): String {
        val groupedOperations = groupOperationsByCurrency(queryResult)
        val groupedBalance = groupBalanceByCurrency(groupedOperations)
        val formattedBalance = formatBalance(groupedBalance)
        return joinBalance(formattedBalance)
    }

    private fun groupOperationsByCurrency(
        queryResult: Result<OperationView>
    ): Map<CurrencyEntry, List<OperationView>> {
        return queryResult.toList().groupBy { CurrencyEntry(it) }
    }

    private fun groupBalanceByCurrency(
        groupedOperations: Map<CurrencyEntry, List<OperationView>>
    ): Map<CurrencyEntry, BigDecimal> {
        return groupedOperations.mapValues { calculateBalance(it.value) }
    }

    private fun calculateBalance(operations: List<OperationView>): BigDecimal {
        val incomeBalance = calculateBalanceByType(operations, ::isIncomeOperation)
        val expenseBalance = calculateBalanceByType(operations, ::isExpenseOperation)
        return incomeBalance.subtract(expenseBalance)
    }

    private fun calculateBalanceByType(
        operations: List<OperationView>,
        typePredicate: (OperationView) -> Boolean
    ): BigDecimal {
        return operations
            .asSequence()
            .filter { operation -> typePredicate(operation) }
            .map { operation -> operation.value }
            .fold(BigDecimal.ZERO, { result, value -> result.add(value) })
    }

    private fun isIncomeOperation(operation: OperationView): Boolean {
        return operation.type in OperationType.incomeTypes
    }

    private fun isExpenseOperation(operation: OperationView): Boolean {
        return operation.type in OperationType.expenseTypes
    }

    private fun formatBalance(groupedBalance: Map<CurrencyEntry, BigDecimal>): List<String> {
        return groupedBalance.map { balance ->
            "${bigDecimalToString(balance.value)} ${balance.key.name}"
        }
    }

    private fun joinBalance(formattedBalance: List<String>): String {
        return if (formattedBalance.isEmpty()) {
            "0"
        } else {
            formattedBalance.joinToString(separator = "; ")
        }
    }
}