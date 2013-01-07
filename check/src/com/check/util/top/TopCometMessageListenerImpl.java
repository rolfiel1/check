package com.check.util.top;

import com.check.util.PPUtil;
import com.taobao.api.domain.NotifyTrade;
import com.taobao.api.internal.stream.message.TopCometMessageListener;

public class TopCometMessageListenerImpl implements TopCometMessageListener {

	private TopApiService topApiService;

	public TopCometMessageListenerImpl(TopApiService topApiService) {
		this.topApiService = topApiService;
	}

	public void onConnectMsg(String message) {
	}

	public void onHeartBeat() {
	}

	public void onReceiveMsg(String message) {
		try {
			Object obj = MessageDecode.decodeMsg(message);
			if (obj instanceof NotifyTopats) { // 异步任务
				System.out.println(message);
				NotifyTopats nt = (NotifyTopats) obj;
				if ("done".equals(nt.getTaskStatus())) {
					String url = topApiService.getTaskResultUrl(nt.getTaskId());
					if (url != null) {
						topApiService.downloadAndProcess(url);
					}
				}
			} else if (obj instanceof NotifyTrade) { // 交易消息
				NotifyTrade nt = (NotifyTrade) obj;
				System.out.println(nt.getSellerNick() + ":" + nt.getTid()); // FIXME 获取订单详情并保存到数据库
				topApiService.getTradeFullInfo(nt.getTid(),PPUtil.getProp("session_sey"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onDiscardMsg(String message) {
	}

	public void onServerUpgrade(String message) {
	}

	public void onServerRehash() {
	}

	public void onServerKickOff() {
	}

	public void onClientKickOff() {
	}

	public void onOtherMsg(String message) {
	}

	public void onException(Exception e) {
	}

}
