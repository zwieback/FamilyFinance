package io.github.zwieback.familyfinance.core.model

import androidx.databinding.Bindable
import io.github.zwieback.familyfinance.core.model.constant.PersonRestriction
import io.github.zwieback.familyfinance.core.model.constant.Views
import io.requery.*

@Entity(propertyNameStyle = PropertyNameStyle.FLUENT_BEAN)
@View(name = Views.PERSON)
interface IPersonView : IBaseEntityFolder {

    @get:Nullable
    @get:Column(name = "parent_id")
    val parentId: Int?

    @get:Bindable
    @get:Nullable
    @get:Column(name = "parent_name", length = PersonRestriction.NAME_MAX_LENGTH)
    val parentName: String?

    @get:Bindable
    @get:Column(nullable = false, length = PersonRestriction.NAME_MAX_LENGTH)
    val name: String

    @get:Bindable
    @get:Column(name = "order_code", nullable = false)
    val orderCode: Int
}
