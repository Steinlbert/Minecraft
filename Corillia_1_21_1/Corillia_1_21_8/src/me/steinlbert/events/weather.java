package me.steinlbert.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class weather implements Listener{

	@EventHandler
	public void weathercontrol (WeatherChangeEvent e)
	{
		//e.getWorld().setWeatherDuration(10);
		e.getWorld().setClearWeatherDuration(10);
		
		
	}	
}
