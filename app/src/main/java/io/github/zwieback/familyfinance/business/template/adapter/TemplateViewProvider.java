package io.github.zwieback.familyfinance.business.template.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;

import io.github.zwieback.familyfinance.R;
import io.github.zwieback.familyfinance.business.template.exception.UnsupportedTemplateTypeException;
import io.github.zwieback.familyfinance.core.adapter.EntityProvider;
import io.github.zwieback.familyfinance.core.model.TemplateView;

class TemplateViewProvider extends EntityProvider<TemplateView> {

    TemplateViewProvider(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public IIcon provideDefaultIcon(TemplateView template) {
        return FontAwesome.Icon.faw_file_alt1;
    }

    @Override
    public int provideDefaultIconColor(TemplateView template) {
        switch (template.getType()) {
            case EXPENSE_OPERATION:
                return R.color.colorExpense;
            case INCOME_OPERATION:
                return R.color.colorIncome;
            case TRANSFER_OPERATION:
                return R.color.colorPrimaryDark;
            default:
                throw new UnsupportedTemplateTypeException(template.getId(), template.getType());
        }
    }

    @Override
    public int provideTextColor(TemplateView template) {
        return ContextCompat.getColor(context, provideDefaultIconColor(template));
    }
}
