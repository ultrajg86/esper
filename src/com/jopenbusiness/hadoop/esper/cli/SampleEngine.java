package com.jopenbusiness.hadoop.esper.cli;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.jopenbusiness.hadoop.esper.event.SampleEvent;
import com.jopenbusiness.hadoop.esper.listener.SampleListener;

public class SampleEngine {
	public static void main(String[] args) {
		Configuration config = null;
		EPServiceProvider service = null;
		EPStatement stat = null;
		String epl = null;
		SampleListener listener = null;
		EPRuntime runtime = null;
		
		//---	ó���� Event�� ����Ͽ� Esper�� ���񽺸� ���� �մϴ�.
		config = new Configuration();
		config.addEventType("SampleEvent", SampleEvent.class.getName());
		service = EPServiceProviderManager.getDefaultProvider(config);
		
		//---	EPL(Event Processing Language)�� ����Ͽ� Statement�� ���� �մϴ�.
		//---	���� 3�� ���� �߻��� �̺�Ʈ�� ��ü ������ ���� ����� ���մϴ�. 
		epl = "select item, count(*), avg(price) from SampleEvent.win:time(3 sec)";
		stat = service.getEPAdministrator().createEPL(epl);
				
		listener = new SampleListener();
		stat.addListener(listener);
		
		//---	Event�� �߻����� ���ϴ�.
		runtime = service.getEPRuntime();
		for (int i = 0;i < 20; i++) {
			runtime.sendEvent(new SampleEvent("aaa_" + i, 10.0 * i));			
			try {
				Thread.sleep(300);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}