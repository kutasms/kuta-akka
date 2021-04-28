package com.kuta.akka.base.auto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.impl.triggers.CronTriggerImpl;

public class FollowSourceTriggerImpl extends CronTriggerImpl implements PluginCronTrigger {

	/**
	 * 
	 */
	private static final long serialVersionUID = 16411446974858690L;

	@Override
	public String getCron(Date time) {
		// TODO Auto-generated method stub
		String dateFormat = "ss mm HH dd MM ? yyyy";
		 
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (time != null) {
            formatTimeStr = sdf.format(time);
        }
        return formatTimeStr;
	}

	
}
