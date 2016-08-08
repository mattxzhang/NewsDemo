package com.kingyon.newslib.uis.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingyon.baseuilib.activities.BaseHeaderActivity;
import com.kingyon.baseuilib.fragments.BaseHeaderFragment;
import com.kingyon.baseuilib.utils.CommonUtil;
import com.kingyon.baseuilib.utils.ConstantUtil;
import com.kingyon.baseuilib.utils.TimeUtil;
import com.kingyon.netlib.callback.AbsAPICallback;
import com.kingyon.netlib.exception.ApiException;
import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.NewsEntity;
import com.kingyon.newslib.nets.NewsNetCloud;
import com.kingyon.newslib.views.JudgeLinearLayout;
import com.zhy.adapter.recyclerview.utils.GlideUtils;
import com.zzhoujay.richtext.ImageFixCallback;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.OnImageClickListener;
import com.zzhoujay.richtext.OnURLClickListener;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arvin on 2016/8/8 10:55
 */
public class ArticleNewsFragment extends BaseHeaderFragment implements View.OnClickListener {
    TextView tvTitle;
    TextView tvSource;
    TextView tvTime;
    TextView tvContent;
    TextView tvCollection;
    TextView tvCommentCount;
    EditText edInput;
    LinearLayout layoutAboutNews;
    JudgeLinearLayout lineToolEd;

    private NewsEntity newsDetail;

    private OnImageClickListener onImageClickListener;
    private InputMethodManager inputManager;
    private OnURLClickListener onUrlClickListener;
    private ImageFixCallback imgFixCallback;

    public ArticleNewsFragment() {
    }

    @SuppressLint("ValidFragment")
    public ArticleNewsFragment(NewsEntity newsDetail) {
        this.newsDetail = newsDetail;
    }

    @Override
    protected String getTitleText() {
        return "";
    }

    @Override
    protected int getContentViewID() {
        return R.layout.nl_fragment_article_news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initEvent();

        inputManager = (InputMethodManager) edInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        initRichText();
    }

    private void initView() {
        tvTitle = getView(R.id.tv_title);
        tvSource = getView(R.id.tv_source);
        tvTime = getView(R.id.tv_time);
        tvContent = getView(R.id.tv_content);
        tvCollection = getView(R.id.btn_collect);
        tvCommentCount = getView(R.id.tv_comment_count);
        edInput = getView(R.id.ed_input);
        layoutAboutNews = getView(R.id.layout_about_news);
        lineToolEd = getView(R.id.line_tool_ed);
    }

    private void initEvent() {
        getView(R.id.tv_comment).setOnClickListener(this);
        getView(R.id.btn_comment).setOnClickListener(this);
        getView(R.id.btn_collect).setOnClickListener(this);
        getView(R.id.btn_share).setOnClickListener(this);
        getView(R.id.btn_send).setOnClickListener(this);
    }

    private void initRichText() {
        onImageClickListener = new OnImageClickListener() {
            @Override
            public void imageClicked(List<String> imageUrls, int position) {
//                showScanDialog(imageUrls, position);
            }
        };
        onUrlClickListener = new OnURLClickListener() {
            @Override
            public boolean urlClicked(String url) {
                CommonUtil.openBrowser(getActivity(), url);
                return true;
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        showProgress("");
        NewsNetCloud.getInstance().getNewsApi().getNewsDetail(newsDetail.getObjectId()).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new AbsAPICallback<NewsEntity>() {

                    @Override
                    public void onNext(NewsEntity newsEntity) {
                        newsDetail = newsEntity;
                        setData();
                        hideProgress();
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        hideProgress();
                    }

                    @Override
                    protected void onResultError(ApiException ex) {
                        mUtil.showToast(ex.getDisplayMessage());
                        hideProgress();
                    }
                });
    }

    private void setData() {
        tvTitle.setText(newsDetail.getTitle());
        tvTime.setText(TimeUtil.getRecentlyTime(newsDetail.getPublishTime()));
        tvSource.setText(newsDetail.getRealSource());
        if (!TextUtils.isEmpty(newsDetail.getContent())) {
            RichText.from(newsDetail.getContent())
                    .autoFix(true)
                    .imageClick(onImageClickListener)
                    .urlClick(onUrlClickListener)
                    .placeHolder(getResources().getDrawable(R.drawable.img_loading))
                    .error(getResources().getDrawable(R.drawable.img_loading))
                    .into(tvContent);
        }
        if (newsDetail.getContentSocail() != null) {
            tvCollection.setSelected(newsDetail.getContentSocail().getIsFavorite());
            setCommentCountText();
        }
    }

    private void setCommentCount(int count) {
        newsDetail.getContentSocail().setCommentedCount(count);
    }

    private void setCommentCountText() {
        int count = newsDetail.getContentSocail().getCommentedCount();
        if (count == 0) {
            tvCommentCount.setVisibility(View.GONE);
        } else {
            tvCommentCount.setVisibility(View.VISIBLE);
            tvCommentCount.setText(count + "");
        }
    }

    private void sendComment() {
        String input = edInput.getText().toString().trim();
        NewsNetCloud.getInstance().getNewsApi().sendComment(newsDetail.getObjectId(), input).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new AbsAPICallback<Object>() {
                    @Override
                    public void onNext(Object newsEntity) {
                        showToast("评论成功~");
                        edInput.setText("");
                        if (newsDetail.getContentSocail() != null) {
                            setCommentCount(newsDetail.getContentSocail().getCommentedCount() + 1);
                            setCommentCountText();
                        }
                        inputManager.hideSoftInputFromWindow(edInput.getWindowToken(), 0);
                    }

                    @Override
                    protected void onResultError(ApiException ex) {
                        mUtil.showToast(ex.getDisplayMessage());
                    }
                });
    }

    private void cancelCollection() {
        NewsNetCloud.getInstance().getNewsApi().cancelCollection(newsDetail.getObjectId()).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new AbsAPICallback<Object>() {
                    @Override
                    public void onNext(Object newsEntity) {
                        showToast("取消收藏成功~");
                    }

                    @Override
                    protected void onResultError(ApiException ex) {
                        mUtil.showToast(ex.getDisplayMessage());
                        tvCollection.setSelected(!tvCollection.isSelected());
                    }
                });
    }

    private void collection() {
        NewsNetCloud.getInstance().getNewsApi().collection(newsDetail.getObjectId()).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new AbsAPICallback<Object>() {

                    @Override
                    public void onNext(Object newsEntity) {
                        showToast("收藏成功~");
                    }

                    @Override
                    protected void onResultError(ApiException ex) {
                        mUtil.showToast(ex.getDisplayMessage());
                        tvCollection.setSelected(!tvCollection.isSelected());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_comment) {
//            if (!CurrentUtil.isLogin(this)) {
//                return;
//            }
            lineToolEd.setVisibility(View.VISIBLE);
            edInput.setFocusable(true);
            edInput.setFocusableInTouchMode(true);
            edInput.requestFocus();
            inputManager.showSoftInput(edInput, 0);
        } else if (i == R.id.btn_comment) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ConstantUtil.PASS_VALUE, newsDetail);
//            mUtil.startActivityWithAnim(NewsCommentActivity.class, bundle);
        } else if (i == R.id.btn_collect) {
//            if (!CurrentUtil.isLogin(this)) {
//                return;
//            }
            tvCollection.setSelected(!tvCollection.isSelected());
            if (tvCollection.isSelected()) {
                collection();
                return;
            }
            cancelCollection();
        } else if (i == R.id.btn_share) {
//            shareDialog.show();
        } else if (i == R.id.btn_send) {
//            if (!CurrentUtil.isLogin(this)) {
//                return;
//            }
            if (TextUtils.isEmpty(edInput.getText().toString().trim())) {
                mUtil.showToast("评论内容不能为空");
                return;
            }
            sendComment();
        }
    }
}
