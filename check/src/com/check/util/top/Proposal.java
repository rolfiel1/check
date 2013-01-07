package com.check.util.top;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.check.util.PPUtil;
import com.taobao.api.AutoRetryTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;

public class Proposal {

	private static final String API_URL = "http://gw.api.taobao.com/router/rest";
//	private static final String APP_KEY = "21286772";
//	private static final String APP_SECRET = "88aadbcd5f28244ea6da67fb968768dc";
//	private static final String SESSION_KEY = "6101428d8e41e95184b480f4bdc96f730a0c11d6ba386881057411533";
	
	private static final String APP_KEY = PPUtil.getProp("app_key");
	private static final String APP_SECRET = PPUtil.getProp("app_secret");
	private static final String SESSION_KEY = PPUtil.getProp("session_sey");
	
	private static Date lastSync;
	private static Map<Long, Boolean> taskIds = new HashMap<Long, Boolean>();

	public static void main(String[] args) throws Exception {
		Proposal.startProposal3();
	}

	/**
	 * 方案一
	 */
	public static void startProposal1() throws Exception {
		final TaobaoClient client = new AutoRetryTaobaoClient(API_URL, APP_KEY,
				APP_SECRET);
		final TopApiService topApiService = new TopApiService(client);

		// 初始化一周前内订单
		final Date end = new Date();
		final Date start = DateUtils.addDays(end, -7);
		// new Thread(new Runnable() {
		// public void run() {
		List<Date[]> dateList = DateUtils.splitTimeByDays(start, end, 1);
		for (Date[] dates : dateList) {
			try {
				topApiService.syncSoldTrades(dates[0], dates[1], SESSION_KEY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// }
		// }).start();
		lastSync = end;

		// 定时获取增量订单
		new Timer().schedule(new TimerTask() {
			public void run() {
				try {
					System.out.println("---定时任务进入---");
					Date _start = DateUtils.addMinutes(lastSync, -10); // 加载上一次的时间，并退后10分钟
					Date _end = new Date();
					topApiService.syncIncrementSoldTrades(_start, _end,
							SESSION_KEY);
					lastSync = _end; // 把本次更新时间保存到数据库
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0L,  3 * 1000L);
	}

	/**
	 * 方案二
	 */
	public static void startProposal2() throws Exception {
		final TaobaoClient client = new AutoRetryTaobaoClient(API_URL, APP_KEY,
				APP_SECRET);
		final TopApiService topApiService = new TopApiService(client);

		// 初始化三个月内订单
		final Date now = new Date();
		String start = DateUtils.formatDay(DateUtils.addMonths(now, -3));
		String end = DateUtils.formatDay(DateUtils.addDays(now, -1));
		taskIds.put(topApiService.asyncSoldTrades(start, end, SESSION_KEY),
				Boolean.FALSE);

		// 定时轮询检查异步任务状态
		new Timer().schedule(new TimerTask() {
			public void run() {
				for (Entry<Long, Boolean> taskId : taskIds.entrySet()) {
					if (!taskId.getValue()) {
						try {
							String url = topApiService.getTaskResultUrl(taskId
									.getKey());
							if (url != null) {
								topApiService.downloadAndProcess(url);
								taskIds.put(taskId.getKey(), Boolean.TRUE);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, 0L, 3 * 60 * 1000L);

		// 获取今天增量订单
		topApiService.syncIncrementSoldTrades(DateUtils.getTodayStartTime(),
				now, SESSION_KEY);
		lastSync = now;

		// 定时获取增量订单
		new Timer().schedule(new TimerTask() {
			public void run() {
				try {
					Date _start = DateUtils.addMinutes(lastSync, -10); // 加载上一次的时间，并退后10分钟
					Date _end = new Date();
					topApiService.syncIncrementSoldTrades(_start, _end,
							SESSION_KEY);
					lastSync = _end; // 把本次最后同步时间保存到数据库
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0L, 5 * 60 * 1000L);
	}

	/**
	 * 方案三
	 */
	public static void startProposal3() throws Exception {
		final TaobaoClient client = new AutoRetryTaobaoClient(API_URL, APP_KEY,
				APP_SECRET);
		final TopApiService topApiService = new TopApiService(client);

		// 启动主动通知监听器
		topApiService.permitUser(SESSION_KEY);
		Configuration conf = new Configuration(APP_KEY, APP_SECRET, null);
		TopCometStream stream = new TopCometStreamFactory(conf).getInstance();
		stream.setConnectionListener(new ConnectionLifeCycleListenerImpl());
		stream.setMessageListener(new TopCometMessageListenerImpl(topApiService));
		stream.start();
		// 初始化前3天内的订单
		final Date end = DateUtils.addDays(new Date(), -1);
		final Date start = DateUtils.addDays(end, -3);
		List<Date[]> dateList = DateUtils.splitTimeByDays(start, end, 1);
		for (Date[] dates : dateList) {
			try {
				topApiService.syncSoldTrades(dates[0], dates[1], SESSION_KEY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 获取今天的增量订单
		topApiService.syncIncrementSoldTrades(DateUtils.getTodayStartTime(),end, SESSION_KEY);
	}

}
