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
		
		//---	처리할 Event를 등록하여 Esper용 서비스를 생성 합니다.
		config = new Configuration();
		config.addEventType("SampleEvent", SampleEvent.class.getName());
		service = EPServiceProviderManager.getDefaultProvider(config);
				
		//---	EPL(Event Processing Language)을 사용하여 Statement를 생성 합니다.
		//epl = "select item, count(*), avg(price) from SampleEvent.win:time(3 sec)";
		epl = "select item, avg(price), count(*) from SampleEvent.win:time(10 sec) where item = 'NFM' ";
		stat = service.getEPAdministrator().createEPL(epl);
				
		listener = new SampleListener();
		stat.addListener(listener);
		
		//---	Event를 발생시켜 봅니다.
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