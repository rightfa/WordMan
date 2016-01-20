package com.qlfsoft.wordman.ui;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qlfsoft.wordman.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class ProcessTotalDiagram extends BaseActivity {

	private Button btn_reply;
	private PieChart chart1;
	private PieChart chart2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.progress_total_diagram);
		btn_reply = (Button) findViewById(R.id.prgtotal_reply);
		chart1 = (PieChart) findViewById(R.id.prgtotal_chart1);
		chart2 = (PieChart) findViewById(R.id.prgtotal_chart2);
		
		setData();
		
	}
	private void setData() {
		Intent intent = getIntent();
		int wordSize = intent.getIntExtra("WORDSIZE", 0);
		int wordStudy = intent.getIntExtra("WORDSTUDY", 0);
		int wordControl = intent.getIntExtra("WORDCONTROL", 0);
		
		chart1.setUsePercentValues(true);
		chart1.setDescription("");
		chart1.setExtraOffsets(5, 10, 5, 5);
		chart1.setDragDecelerationFrictionCoef(0.95f);
		chart1.setDrawHoleEnabled(false);
		chart1.setTransparentCircleColor(Color.WHITE);
		chart1.setTransparentCircleAlpha(110);
		chart1.setHighlightPerTapEnabled(true);
		Legend l1 = chart1.getLegend();
		l1.setPosition(LegendPosition.RIGHT_OF_CHART);
		l1.setXEntrySpace(7f);
		l1.setYEntrySpace(0f);
		l1.setYOffset(0f);
		ArrayList<String> xVals1 = new ArrayList<String>();
		xVals1.add("词汇量");
		xVals1.add("已学习");
		
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		yVals1.add(new Entry(wordSize,0));
		yVals1.add(new Entry(wordStudy,1));
		
		PieDataSet dataset1 = new PieDataSet(yVals1,"学习情况");
		dataset1.setSliceSpace(2f);
		dataset1.setSelectionShift(5f);
		ArrayList<Integer> colors = new ArrayList<Integer>();
		for(int c:ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);
		for(int c:ColorTemplate.COLORFUL_COLORS)
			colors.add(c);
		colors.add(ColorTemplate.getHoloBlue());
		dataset1.setColors(colors);
		PieData data1 = new PieData(xVals1,dataset1);
		data1.setValueFormatter(new PercentFormatter());
		data1.setValueTextSize(13f);
		data1.setValueTextColor(Color.WHITE);
		chart1.setData(data1);
		chart1.highlightValue(null);
		chart1.invalidate();
		
		chart2.setUsePercentValues(true);
		chart2.setDescription("");
		chart2.setExtraOffsets(5, 10, 5, 5);
		chart2.setDragDecelerationFrictionCoef(0.95f);
		chart2.setDrawHoleEnabled(false);
		chart2.setTransparentCircleColor(Color.WHITE);
		chart2.setTransparentCircleAlpha(110);
		chart2.setHighlightPerTapEnabled(true);
		Legend l2 = chart2.getLegend();
		l2.setPosition(LegendPosition.RIGHT_OF_CHART);
		l2.setXEntrySpace(7f);
		l2.setYEntrySpace(0f);
		l2.setYOffset(0f);
		ArrayList<String> xVals2 = new ArrayList<String>();
		xVals2.add("已学习");
		xVals2.add("已掌握");
		
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		yVals2.add(new Entry(wordStudy,0));
		yVals2.add(new Entry(wordControl,1));
		
		PieDataSet dataset2 = new PieDataSet(yVals2,"掌握情况");
		dataset2.setSliceSpace(2f);
		dataset2.setSelectionShift(5f);
		dataset2.setColors(colors);
		PieData data2 = new PieData(xVals2,dataset2);
		data2.setValueFormatter(new PercentFormatter());
		data2.setValueTextSize(13f);
		data2.setValueTextColor(Color.WHITE);
		chart2.setData(data2);
		chart2.highlightValue(null);
		chart2.invalidate();	
	}
	
}
