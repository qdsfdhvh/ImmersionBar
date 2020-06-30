package com.gyf.immersionbar.sample.activity;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gyf.immersionbar.sample.R;
import com.gyf.immersionbar.sample.fragment.two.CategoryTwoFragment;
import com.gyf.immersionbar.sample.fragment.two.HomeTwoFragment;
import com.gyf.immersionbar.sample.fragment.two.MineTwoFragment;
import com.gyf.immersionbar.sample.fragment.two.ServiceTwoFragment;

import butterknife.BindView;

/**
 * @author geyifeng
 * @date 2017/7/20
 */
public class FragmentTwoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_category)
    LinearLayout llCategory;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;

    private HomeTwoFragment homeTwoFragment;
    private CategoryTwoFragment categoryTwoFragment;
    private ServiceTwoFragment serviceTwoFragment;
    private MineTwoFragment mineTwoFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment_two;
    }

    @Override
    protected void initView() {
        selectedFragment(0);
        tabSelected(llHome);
    }

    @Override
    protected void setListener() {
        llHome.setOnClickListener(this);
        llCategory.setOnClickListener(this);
        llService.setOnClickListener(this);
        llMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                selectedFragment(0);
                tabSelected(llHome);
                break;
            case R.id.ll_category:
                selectedFragment(1);
                tabSelected(llCategory);
                break;
            case R.id.ll_service:
                selectedFragment(2);
                tabSelected(llService);
                break;
            case R.id.ll_mine:
                selectedFragment(3);
                tabSelected(llMine);
                break;
            default:
                break;
        }
    }

    private void selectedFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (homeTwoFragment == null) {
                    homeTwoFragment = new HomeTwoFragment();
                    transaction.add(R.id.content, homeTwoFragment);
                } else {
                    transaction.show(homeTwoFragment);
                    transaction.setMaxLifecycle(homeTwoFragment, Lifecycle.State.RESUMED);
                }
                break;
            case 1:
                if (categoryTwoFragment == null) {
                    categoryTwoFragment = new CategoryTwoFragment();
                    transaction.add(R.id.content, categoryTwoFragment);
                } else {
                    transaction.show(categoryTwoFragment);
                    transaction.setMaxLifecycle(categoryTwoFragment, Lifecycle.State.RESUMED);
                }
                break;
            case 2:
                if (serviceTwoFragment == null) {
                    serviceTwoFragment = new ServiceTwoFragment();
                    transaction.add(R.id.content, serviceTwoFragment);
                } else {
                    transaction.show(serviceTwoFragment);
                    transaction.setMaxLifecycle(serviceTwoFragment, Lifecycle.State.RESUMED);
                }
                break;
            case 3:
                if (mineTwoFragment == null) {
                    mineTwoFragment = new MineTwoFragment();
                    transaction.add(R.id.content, mineTwoFragment);
                } else {
                    transaction.show(mineTwoFragment);
                    transaction.setMaxLifecycle(mineTwoFragment, Lifecycle.State.RESUMED);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homeTwoFragment != null) {
            transaction.hide(homeTwoFragment);
            transaction.setMaxLifecycle(homeTwoFragment, Lifecycle.State.CREATED);
        }
        if (categoryTwoFragment != null) {
            transaction.hide(categoryTwoFragment);
            transaction.setMaxLifecycle(categoryTwoFragment, Lifecycle.State.CREATED);
        }
        if (serviceTwoFragment != null) {
            transaction.hide(serviceTwoFragment);
            transaction.setMaxLifecycle(serviceTwoFragment, Lifecycle.State.CREATED);
        }
        if (mineTwoFragment != null) {
            transaction.hide(mineTwoFragment);
            transaction.setMaxLifecycle(mineTwoFragment, Lifecycle.State.CREATED);
        }
    }

    private void tabSelected(LinearLayout linearLayout) {
        llHome.setSelected(false);
        llCategory.setSelected(false);
        llService.setSelected(false);
        llMine.setSelected(false);
        linearLayout.setSelected(true);
    }
}
