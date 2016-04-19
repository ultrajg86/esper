package com.jopenbusiness.hadoop.esper.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class SampleListener implements UpdateListener {
	public void update(EventBean[] eventNew, EventBean[] eventOld) {
		if (eventNew != null) {			
			//UtilLogger.info.println(logCaller, "--- Event New : " + eventNew.length);
			System.out.println("--- Event New : " + eventNew.length);
			for (Integer idx = 0; idx < eventNew.length; idx++) {
				//displayEvent(idx, eventNew[idx]);
				System.out.println(idx + " / " + eventNew[idx]);
			}
		}

		if (eventOld != null) {
			//UtilLogger.info.println(logCaller, "--- Event Old : " + eventOld.length);
			System.out.println("--- Event Old : " + eventOld.length);
			for (Integer idx = 0; idx < eventOld.length; idx++)  {
				//displayEvent(idx, eventOld[idx]);
				System.out.println(idx + " / " + eventNew[idx]);
			}
		}
		//UtilLogger.info.println(logCaller, "");
	}
}