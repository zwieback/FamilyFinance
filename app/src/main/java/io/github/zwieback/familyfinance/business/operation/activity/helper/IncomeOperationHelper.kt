package io.github.zwieback.familyfinance.business.operation.activity.helper

import android.content.Context
import android.content.Intent
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_ACCOUNT_ID
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_ARTICLE_ID
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_CURRENCY_ID
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_DATE
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_DESCRIPTION
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_EXCHANGE_RATE_ID
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_OPERATION_ID
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_OWNER_ID
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_URL
import io.github.zwieback.familyfinance.business.operation.activity.IncomeOperationEditActivity.Companion.INPUT_INCOME_VALUE
import io.github.zwieback.familyfinance.business.operation.filter.IncomeOperationFilter
import io.github.zwieback.familyfinance.core.model.*
import io.github.zwieback.familyfinance.core.model.type.OperationType
import io.github.zwieback.familyfinance.util.DateUtils
import io.github.zwieback.familyfinance.util.NumberUtils
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import io.requery.Persistable
import io.requery.reactivex.ReactiveEntityStore
import org.threeten.bp.LocalDate
import java.math.BigDecimal

class IncomeOperationHelper(context: Context, data: ReactiveEntityStore<Persistable>) :
    OperationHelper<IncomeOperationFilter>(context, data) {

    override fun getIntentToAdd(): Intent {
        return getEmptyIntent()
    }

    override fun getIntentToAdd(
        articleId: Int?,
        accountId: Int?,
        transferAccountId: Int?,
        ownerId: Int?,
        currencyId: Int?,
        exchangeRateId: Int?,
        date: LocalDate?,
        value: BigDecimal?,
        description: String?,
        url: String?
    ): Intent {
        val intent = getEmptyIntent()
        return getIntentToAdd(
            intent,
            articleId,
            accountId, transferAccountId,
            ownerId,
            currencyId, exchangeRateId,
            date, value,
            description, url
        )
    }

    override fun getIntentToAdd(
        preparedIntent: Intent,
        articleId: Int?,
        accountId: Int?,
        transferAccountId: Int?,
        ownerId: Int?,
        currencyId: Int?,
        exchangeRateId: Int?,
        date: LocalDate?,
        value: BigDecimal?,
        description: String?,
        url: String?
    ): Intent {
        if (articleId != databasePrefs.incomesArticleId) {
            preparedIntent.putExtra(INPUT_INCOME_ARTICLE_ID, articleId)
        }
        accountId?.let {
            preparedIntent.putExtra(INPUT_INCOME_ACCOUNT_ID, accountId)
        }
        ownerId?.let {
            preparedIntent.putExtra(INPUT_INCOME_OWNER_ID, ownerId)
        }
        currencyId?.let {
            preparedIntent.putExtra(INPUT_INCOME_CURRENCY_ID, currencyId)
        }
        exchangeRateId?.let {
            preparedIntent.putExtra(INPUT_INCOME_EXCHANGE_RATE_ID, exchangeRateId)
        }
        date?.let {
            DateUtils.writeLocalDateToIntent(preparedIntent, INPUT_INCOME_DATE, date)
        }
        value?.let {
            NumberUtils.writeBigDecimalToIntent(preparedIntent, INPUT_INCOME_VALUE, value)
        }
        if (!description.isNullOrEmpty()) {
            preparedIntent.putExtra(INPUT_INCOME_DESCRIPTION, description)
        }
        if (!url.isNullOrEmpty()) {
            preparedIntent.putExtra(INPUT_INCOME_URL, url)
        }
        return preparedIntent
    }

    override fun getIntentToAdd(filter: IncomeOperationFilter?): Intent {
        return if (filter == null) {
            getEmptyIntent()
        } else {
            getIntentToAdd(
                filter.getArticleId(), filter.getAccountId(), null,
                filter.getOwnerId(), filter.getCurrencyId(), null, null, null, null, null
            )
        }
    }

    override fun getIntentToEdit(operation: OperationView): Intent {
        return getEmptyIntent()
            .putExtra(INPUT_INCOME_OPERATION_ID, operation.id)
    }

    override fun getIntentToDuplicate(operation: OperationView): Intent {
        return getIntentToAdd(
            operation.articleId,
            operation.accountId,
            null,
            operation.ownerId,
            operation.currencyId,
            operation.exchangeRateId,
            operation.date,
            operation.value,
            operation.description,
            operation.url
        )
    }

    override fun getEmptyIntent(): Intent {
        return Intent(context, IncomeOperationEditActivity::class.java)
    }

    override fun validToAddImmediately(
        articleId: Int?,
        accountId: Int?,
        transferAccountId: Int?,
        ownerId: Int?,
        currencyId: Int?,
        exchangeRateId: Int?,
        date: LocalDate?,
        value: BigDecimal?,
        description: String?,
        url: String?
    ): Boolean {
        return (articleId != null
                && accountId != null
                && ownerId != null
                && exchangeRateId != null
                && date != null
                && value != null)
    }

    override fun getIntentToAddImmediately(
        articleId: Int?,
        accountId: Int?,
        transferAccountId: Int?,
        ownerId: Int?,
        currencyId: Int?,
        exchangeRateId: Int?,
        date: LocalDate?,
        value: BigDecimal?,
        description: String?,
        url: String?
    ): Intent {
        val intent = getEmptyIntentToAddImmediately()
        return getIntentToAdd(
            intent,
            articleId,
            accountId, transferAccountId,
            ownerId,
            currencyId, exchangeRateId,
            date, value,
            description, url
        )
    }

    override fun getEmptyIntentToAddImmediately(): Intent {
        return super.getEmptyIntentToAddImmediately()
            .putExtra(INPUT_OPERATION_TYPE, OperationType.INCOME_OPERATION)
    }

    override fun addOperationImmediately(
        intent: Intent,
        onSuccess: Consumer<Operation>
    ): Disposable {
        val accountId = intent.getIntExtra(INPUT_INCOME_ACCOUNT_ID, 0)
        val articleId = intent.getIntExtra(INPUT_INCOME_ARTICLE_ID, 0)
        val ownerId = intent.getIntExtra(INPUT_INCOME_OWNER_ID, 0)
        val exchangeRateId = intent.getIntExtra(INPUT_INCOME_EXCHANGE_RATE_ID, 0)
        val date = DateUtils.readLocalDateFromIntent(intent, INPUT_INCOME_DATE)
        val value = NumberUtils.readBigDecimalFromIntent(intent, INPUT_INCOME_VALUE)
        val description = intent.getStringExtra(INPUT_INCOME_DESCRIPTION)
        val url = intent.getStringExtra(INPUT_INCOME_URL)
        return Maybe.zip<Account, Article, Person, ExchangeRate, Operation>(
            data.findByKey(Account::class.java, accountId),
            data.findByKey(Article::class.java, articleId),
            data.findByKey(Person::class.java, ownerId),
            data.findByKey(ExchangeRate::class.java, exchangeRateId),
            Function4 { account, article, owner, exchangeRate ->
                Operation()
                    .setType(OperationType.INCOME_OPERATION)
                    .setAccount(account)
                    .setArticle(article)
                    .setOwner(owner)
                    .setExchangeRate(exchangeRate)
                    .setDate(date)
                    .setValue(value)
                    .setDescription(description)
                    .setUrl(url)
            }
        )
            .flatMapSingle { data.insert(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess)
    }
}