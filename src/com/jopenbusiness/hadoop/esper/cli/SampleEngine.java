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
		//epl = "select item, count(*), avg(price) from SampleEvent.win:time(3 sec)";
		epl = "select item, avg(price), count(*) from SampleEvent.win:time(10 sec) where item = 'NFM' ";
		stat = service.getEPAdministrator().createEPL(epl);
				
		listener = new SampleListener();
		stat.addListener(listener);
		
		//---	Event�� �߻����� ���ϴ�.
		runtime = service.getEPRuntime();
		for (int i = 0;i < 20; i++) {
			
			double price = Math.random() * 1000;
			double randCandidate = Math.random();

			String candidate;
			if(randCandidate > 0.6){
				candidate = "NFM";
			}else if(randCandidate > 0.3){
				candidate = "Tim";
			}else{
				candidate = "Kim";
			}
			
			System.out.println(candidate + " / Send Event!!!");
			runtime.sendEvent(new SampleEvent(candidate, price));			
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}