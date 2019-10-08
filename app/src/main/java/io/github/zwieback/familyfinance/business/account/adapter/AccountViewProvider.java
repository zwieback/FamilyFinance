package io.github.zwieback.familyfinance.business.account.adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial;

import io.github.zwieback.familyfinance.R;
import io.github.zwieback.familyfinance.core.adapter.EntityProvider;
import io.github.zwieback.familyfinance.core.model.AccountView;

public class AccountViewProvider extends EntityProvider<AccountView> {

    public AccountViewProvider(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public IIcon provideDefaultIcon(AccountView account) {
        if (account.isFolder()) {
            return account.isActive()
                    ? CommunityMaterial.Icon.cmd_folder
                    : CommunityMaterial.Icon.cmd_folder_remove;
        }
        return CommunityMaterial.Icon2.cmd_wallet;
    }

    @Override
    public int provideDefaultIconColor(AccountView account) {
        return R.color.colorPrimaryDark;
    }
}
