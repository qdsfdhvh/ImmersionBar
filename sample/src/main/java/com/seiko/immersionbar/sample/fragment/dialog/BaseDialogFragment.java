package com.seiko.immersionbar.sample.fragment.dialog;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seiko.immersionbar.sample.AppManager;
import com.seiko.immersionbar.sample.R;
import com.seiko.immersionbar.sample.utils.Utils;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DialogFragment 实现沉浸式的基类
 *
 * @author geyifeng
 * @date 2017 /8/26
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected Window mWindow;
    private Unbinder unbinder;
    public Integer[] mWidthAndHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //点击外部消失
        dialog.setCanceledOnTouchOutside(true);
        mWindow = dialog.getWindow();
        mWidthAndHeight = Utils.getWidthAndHeight(mWindow);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initData();
        initView(view);
        setListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppManager.getInstance().hideSoftKeyBoard(requireActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mWidthAndHeight = Utils.getWidthAndHeight(mWindow);
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {}


    /**
     * 初始化数据
     */
    protected void initData() {}

    /**
     * view与数据绑定
     */
    protected void initView(@NonNull View view) {
        ImageView iv = view.findViewById(R.id.mIv);
        if (iv != null) {
            Glide.with(this).asBitmap().load(Utils.getPic())
                    .apply(new RequestOptions().placeholder(R.mipmap.test))
                    .into(iv);
        }
    }

    /**
     * 设置监听
     */
    protected void setListener() {}
}
