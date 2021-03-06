package io.github.zwieback.familyfinance.business.article.lifecycle.destroyer

import android.content.Context
import io.github.zwieback.familyfinance.R
import io.github.zwieback.familyfinance.core.lifecycle.destroyer.EntityDestroyer
import io.github.zwieback.familyfinance.core.lifecycle.destroyer.EntityFromDestroyer
import io.github.zwieback.familyfinance.core.model.Article
import io.github.zwieback.familyfinance.core.model.Operation
import io.github.zwieback.familyfinance.core.model.type.OperationType.TRANSFER_INCOME_OPERATION
import io.requery.Persistable
import io.requery.query.Condition
import io.requery.reactivex.ReactiveEntityStore

internal class ArticleFromTransferOperationsDestroyer(
    context: Context,
    data: ReactiveEntityStore<Persistable>
) : EntityFromDestroyer<Article, Operation>(context, data) {

    override val fromClass: Class<Operation>
        get() = Operation::class.java

    override val alertResourceId: Int
        get() = R.string.transfer_operations_with_article_exists

    override fun next(): EntityDestroyer<Article>? {
        return ArticleFromPreferencesDestroyer(context, data)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun getWhereCondition(article: Article): Condition<*, *> {
        return Operation.TYPE.`in`(TRANSFER_INCOME_OPERATION, TRANSFER_INCOME_OPERATION)
            .and(Operation.ARTICLE_ID.eq(article.id))
    }
}
