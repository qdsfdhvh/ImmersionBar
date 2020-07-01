package com.seiko.immersionbar.sample.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.AppManager
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.Utils
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setTitleBar
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * @author gyf
 * @date 2016/10/24
 */
class PicActivity : SwipeBackActivity() {

    @BindView(R.id.text_view)
    lateinit var textView: TextView

    @BindView(R.id.seek_bar)
    lateinit var seekBar: SeekBar

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.mIv)
    lateinit var mIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        setContentView(R.layout.activity_pic)
        ButterKnife.bind(this)

        setTitleBar(toolbar)
        immersionBar {
            transparentBar()
        }
        Glide.with(this).asBitmap().load(Utils.getFullPic())
            .apply(RequestOptions().placeholder(R.drawable.pic_all))
            .into(mIv)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val alpha = progress.toFloat() / 100
                textView.text = String.format("透明度:%.2ff", alpha)
                immersionBar {
                    addViewSupportTransformColor(toolbar)
                    statusBarColorTransform(getResColor(R.color.colorPrimary))
                    navigationBarColorTransform(getResColor(R.color.orange))
                    barAlpha(alpha)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }
}