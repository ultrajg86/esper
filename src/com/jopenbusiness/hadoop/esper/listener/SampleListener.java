package com.jopenbusiness.hadoop.esper.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class SampleListener implements UpdateListener {
	public void update(EventBean[] eventNew, EventBean[] eventOld) {
		if (eventNew != null) {
			//System.out.println("--- Event New : " + eventNew.length);
			for (Integer idx = 0; idx < eventNew.length; idx++) {
				EventBean eventBean = eventNew[idx];
				//System.out.println(eventBean.get("item") + " / " + eventBean.get("price"));
				System.out.println(eventBean.get("item") + " / " + eventBean.get("count(*)"));
			}
		}

		if (eventOld != null) {
			System.out.println("--- Event Old : " + eventOld.length);
			for (Integer idx = 0; idx < eventOld.length; idx++)  {
				System.out.println(idx + " / " + eventNew[idx]);
			}
		}
		//UtilLogger.info.println(logCaller, "");
	}
}