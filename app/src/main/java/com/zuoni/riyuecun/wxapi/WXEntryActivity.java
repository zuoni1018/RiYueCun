package com.zuoni.riyuecun.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zuoni.common.utils.LogUtil;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.ui.activity.base.BaseActivity;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IWXAPI mWxApi= WXAPIFactory.createWXAPI(this, GlobalVariable.WX_APP_ID);
		mWxApi.handleIntent(getIntent(),this);
	}

	@Override
	public int setLayoutId() {
		return R.layout.we;
	}

	@Override
	public void onReq(BaseReq baseReq) {
		LogUtil.i("zzzzzzzzzzzz1");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.i("微信回调页面销毁了");
	}

	/**
	 * 我们发送给微信请求的时候会调这个方法
	 * */
	@Override
	public void onResp(BaseResp resp) {
		String result;
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result ="分享成功";
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result ="取消分享";
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = "发送被拒绝";
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				result = "不支持错误";
				break;
			default:
				result = "发送返回";
				break;
		}
		LogUtil.i("微信分享",result);
//		finish();
	}

	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {
		LogUtil.i("zzzzzzzzzzzz3");
	}
}