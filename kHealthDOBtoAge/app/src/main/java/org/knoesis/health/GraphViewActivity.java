package org.knoesis.health;

import java.sql.Timestamp;
import java.util.*;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.SensorDataHolder;










import com.ibm.icu.text.SimpleDateFormat;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GraphViewActivity extends KHealthAsthmaParentActivity {
	//GraphView init
	GraphView graph;
	GraphViewSeries series;
	GraphViewData[] dataPoints;
	
    
	
	
	
	
	// Chart data set and renderer
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	private TimeSeries observationsSeries;
	private XYSeriesRenderer graphRenderer;
	private GraphicalView mChartView;
	Date date=new Date();
	Timestamp t=new Timestamp(date.getTime());
	GregorianCalendar calendar = new GregorianCalendar();
	
	//Sensor name and related titles
	private int graphId;
	private Constants.graphs graphs;
	private String yTitle;
	private String timeSeriesName;
	private TextView titeleText;
	
	private Boolean populationSignal=false;
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_observations_graph);
		Bundle extras = getIntent().getExtras();
		titeleText=(TextView)findViewById(R.id.title_all_observations_graph);
		if (extras != null) 
		{
			//if the activity invoked with the graph id
			graphId=extras.getInt("SensorName");
			//Check whether the graphId is with in the bounds or not
			if(graphId >= 0 && graphId < Constants.graphs.values().length)
				graphs = Constants.graphs.values()[graphId];
			
			switch(graphs)
			{
			case no:
				yTitle="Nitric Oxide ppm";
				timeSeriesName="Nitric Oxide Observations";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.no_graph);
				break;
			case co:
				yTitle="co";
				timeSeriesName="CO Observations";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.co_graph);
				break;
			case humidity:
				yTitle="humidity";
				timeSeriesName="Humidity Observations";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.humidity_graph);
				break;
			case temperature:
				yTitle="Temperature";
				timeSeriesName="Temperature Observations";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.temp_graph);
				break;
			case cough:
				yTitle=addLevels(Constants.wake_with_cough);
				timeSeriesName="Wake with cough or not";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.cough_graph);
				break;
			case sleep:
				yTitle=addLevels(Constants.inhaler_at_night);
				timeSeriesName="Used inhaler at night or not";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.sleep_graph);
				break;
			case wheezing:
				yTitle=addLevels(Constants.wheeze);
				timeSeriesName="Wheezing";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.wheezing_graph);
				break;
			case activity:
				yTitle=addLevels(Constants.activity);
				timeSeriesName="Activity Level";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.activity_graph);
				break;
			case inhalerusage:
				yTitle="Number of times inhaler used";
				timeSeriesName="Inhaler Usage";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.inhaler_usage_graph);
				break;
			case pollenLevel:
				graphId=Constants.sensors.pollenLevel.ordinal();
				populationSignal=true;
				yTitle=addLevels(Constants.PollenLevel);
				timeSeriesName="Population Signals";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.pollen_level);
				break;
			case AQI:
				graphId=Constants.sensors.AQI.ordinal();
				populationSignal=true;
				yTitle="Air Quality Index";
				timeSeriesName="Population Signals";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.air_quality_index);
				break;
			case outdoorTemperature:
				graphId=Constants.sensors.outdoorTemperature.ordinal();
				populationSignal=true;
				yTitle="Outdoor Temperature";
				timeSeriesName="Population Signals";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.outdoor_temperature);
				break;
			case outdoorHumidity:
				graphId=Constants.sensors.outdoorHumidity.ordinal();
				populationSignal=true;
				yTitle="Outdoor Humidity";
				timeSeriesName="Population Signals";
				//Setting the custom title based on the sensor name
				titeleText.setText(R.string.outdoor_humidity);
				break;
				
			}
				
				
			
				
			
		}
		else
		{
			//If the this activity not invoked with any sensor name then we use the default sensor as the NO.
			yTitle="Nitric Oxide ppm";
			timeSeriesName="Nitric Oxide Observations";
			//Setting the custom title based on the sensor name
			titeleText.setText(R.string.no_graph);
		}
		
		//calendar.set(2013, 8, 26);
		
		// set legend
		graph.setShowLegend(true);
		graph.setLegendAlign(LegendAlign.BOTTOM);
		graph.setLegendWidth(200);
		
		
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(100, 255, 255, 255));
		mRenderer.setGridColor(Color.argb(100, 0, 0, 0));
		mRenderer.setMarginsColor(Color.argb(100, 255, 255, 255));
		mRenderer.setAxisTitleTextSize(20);
		mRenderer.setChartTitleTextSize(30);
		mRenderer.setLabelsTextSize(20);
		mRenderer.setLegendTextSize(20);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setPointSize(4);
		mRenderer.setGridColor(1);
		//mRenderer.setYAxisMin(0);
		//mRenderer.setYAxisMax(1);
//		mRenderer.setXAxisMin(date.getDate());
//		mRenderer.setXAxisMax(date.getDate()+5);
		mRenderer.setYTitle(yTitle);
		mRenderer.setXTitle("Date");

		observationsSeries = new TimeSeries(timeSeriesName);
		mDataset.addSeries(observationsSeries);
		graphRenderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(graphRenderer);
		graphRenderer.setPointStyle(PointStyle.CIRCLE);
		graphRenderer.setFillPoints(true);
		graphRenderer.setColor(Color.argb(100, 0, 0, 255));
		graphRenderer.setColor(Color.argb(100, 255, 0, 0));
		
		//check for population signals
		if(populationSignal==true)
		{
			getPopulationSignals();
			populationSignal=false;
		}
		else
		{
			getObservations();
		}

	}
	private String addLevels(String[] levels)
	{
		String allLevels="";
		for(int i=0;i<levels.length;i++)
		{
			allLevels+=String.valueOf(i)+"-"+levels[i]+"  ";
		}
		return allLevels;
	}

	protected void onResume() {
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getTimeChartView(this, mDataset,
					mRenderer, "dd-MMM-yyyy");
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(100);
			

			mChartView.setOnLongClickListener(new View.OnLongClickListener() {
				// @Override
				public boolean onLongClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						
						return false; // no chart element was long pressed, so
										// let something
						// else handle the event
					} else {
						
						return true; // the element was long pressed - the event
										// has been
						// handled
					}
				}
			});

			layout.addView(mChartView);

		} else {
			mChartView.repaint();
		}

		super.onResume();
	}

	private void getPopulationSignals() {
		double min,max,value;
		min=0;
		max=1;
		List<SensorDataHolder> list=null;
		
			list=db.getAllSensorObservations(String.valueOf(graphId),asthmaApp.userLoggedIn);
			//Check whether at least single observation exist for this sensor in the observations table
			if(list!=null && list.size() > 0){
				for(SensorDataHolder sensorData:list)
				{
					value=sensorData.getvalue();
					//Calculating minimum and maximum values to set the Y axis limits based on observation values.
					if(min>value)
						min=value;
					if(max<value)
						max=value;
					//This will extract the day,month,year from the timestamp
					sensorData.splitTimestamp();
					//Adding the observation and relative date to the graph.
					observationsSeries.add(returnDateFromCalendar(sensorData.year, sensorData.month, sensorData.day, sensorData.hours,sensorData.minutes), value);
				}
				//Setting the Y axis minimum and maximum values based on the observations values.
				
				mRenderer.setYAxisMin(min);
				mRenderer.setYAxisMax(max);
			}
			else
			{
				//Showing the toast message message when there are no records.
				quickMessage(Constants.error_no_records);
			}
		
	}

	private void getObservations() {
		double min,max,value;
		min=0;
		max=1;
		List<SensorDataHolder> list=null;
		//Check whether the graphs are below 4 then they are sensors 
		if(graphId<=Constants.differentiator)
		{
			list=db.getAllSensorObservations(String.valueOf(graphId),asthmaApp.userLoggedIn);
			//Check whether at least single observation exist for this sensor in the observations table
			if(list!=null && list.size() > 0){
				//instantiate the new array to hold the data points for the GraphView
				dataPoints = new GraphViewData[list.size()];
				//counter to increase the location in the array
				int counter = 0;
				graph.setCustomLabelFormatter(new CustomLabelFormatter() {
					  @Override
					  public String formatLabel(double value, boolean isValueX) {
						
					    if (isValueX) {
					    
					    }
					    return null; // let graphview generate Y-axis label for us
					  }
					});
				for(SensorDataHolder sensorData:list)
				{
					value=sensorData.getvalue();
					//Calculating minimum and maximum values to set the Y axis limits based on observation values.
					if(min>value)
						min=value;
					if(max<value)
						max=value;
					//This will extract the day,month,year from the timestamp
					sensorData.splitTimestamp();
					//Adding the observation and relative date to the graph.
					observationsSeries.add(returnDateFromCalendar(sensorData.year, sensorData.month, sensorData.day, sensorData.hours,sensorData.minutes), value);
					//DataPoints[counter] = new GraphViewData(returnDateFromCalendar(sensorData.year, sensorData.month, sensorData.day, sensorData.hours,sensorData.minutes), value)
				}
				//Setting the Y axis minimum and maximum values based on the observations values.
				
				mRenderer.setYAxisMin(min);
				mRenderer.setYAxisMax(max);
			}
			else
			{
				//Showing the toast message message when there are no records.
				quickMessage(Constants.error_no_records);
			}
		}
		else
		{
			//if the graphId is more than 3 then it is questionnaire graphs for that do the following
			int questionID=(graphId-Constants.differentiator);
			list=db.getAllAnswers(questionID,asthmaApp.userLoggedIn);
			//setting the default max and minimum values
			switch (graphs) {
			case cough:
					//minimum is zero default 
					min=0;
					//maximum is length of the cough levels
					max=Constants.wake_with_cough.length;
				break;
			case sleep:
				//minimum is zero default 
				min=0;
				//maximum is length of the sleep levels
				max=Constants.inhaler_at_night.length;
				break;
				
			case wheezing:
				//minimum is zero default 
				min=0;
				//maximum is length of the wheeze levels
				max=Constants.wheeze.length;
				break;
			case activity:
				//minimum is zero default 
				min=0;
				//maximum is length of the activity levels
				max=Constants.activity.length;
				break;
			case inhalerusage:
				//minimum is zero default 
				min=0;
				//maximum is length of the inhaler usage
				max=Constants.rescueInhaler.length;
				break;

			default:min=0;
			max=1;
				break;
			}
			//Check whether at least single observation exist for this sensor in the observations table
			if(list!=null && list.size() > 0){
				for(SensorDataHolder sensorData:list)
				{
					value=sensorData.getAnswer();
					//Calculating minimum and maximum values to set the Y axis limits based on observation values.
					if(min>value)
						min=value;
					if(max<value)
						max=value;
					//This will extract the day,month,year from the timestamp
					sensorData.splitTimestamp();
					//Adding the observation and relative date to the graph.
					observationsSeries.add(returnDateFromCalendar(sensorData.year, sensorData.month, sensorData.day, sensorData.hours,sensorData.minutes), value);
				}
				//Setting the Y axis minimum and maximum values based on the observations values.
				
				mRenderer.setYAxisMin(min);
				mRenderer.setYAxisMax(max);
			}
			else
			{
				//Showing the toast message message when there are no records.
				quickMessage(Constants.error_no_records);
			}
			
		}
		
		
		

	}

	
	
	private Date returnDateFromCalendar(int year, int month, int day, int hours, int minutes ){
		calendar.set(year, month-1, day, hours, minutes);
		Date date = calendar.getTime();
		return date;
	}
	
	public static String getDate(long milliSeconds, String dateFormat)
	{
	    // Create a DateFormatter object for displaying date in specified format.
	    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

	    // Create a calendar object that will convert the date and time value in milliseconds to date. 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     return formatter.format(calendar.getTime());
	}
	

}
