package com.qlfsoft.wordman.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.qlfsoft.wordman.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProcessDailyDiagram extends BaseActivity {

	private Button btn_reply;
	private BarChart barchart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_daily_diagram);
		btn_reply = (Button) this.findViewById(R.id.prgdaily_reply);
		barchart = (BarChart) findViewById(R.id.prgdaily_barchart);
		
		initChart();
		btn_reply.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
				
			}
			
		});
	}
	private void initChart() {
		Intent intent = getIntent();
		int haveStudy = intent.getIntExtra("HAVESTUDY", 0);
		int needStudy = intent.getIntExtra("NEEDSTUDY", 0);
		int needReview = intent.getIntExtra("NEEDREVIEW", 0);
		int haveReview = intent.getIntExtra("HAVEREVIEW", 0);
		
		barchart.setDrawBarShadow(false);
		barchart.setDescription("");
		barchart.setMaxVisibleValueCount(4);
		barchart.setPinchZoom(false);
		barchart.setDrawGridBackground(false);
		XAxis xAxis = barchart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);
		xAxis.setSpaceBetweenLabels(2);
		
		YAxis leftAxis = barchart.getAxisLeft();
		leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
		leftAxis.setSpaceTop(15f);
		barchart.getAxisRight().setEnabled(false);
		
		String[] ays = getResources().getStringArray(R.array.XdailyDiagram);
		ArrayList<String> xVals = new ArrayList<String>();
		for(int i = 0 ;i< ays.length; i++)
			xVals.add(ays[i]);
		ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
		yVals.add(new BarEntry(needStudy,0));
		yVals.add(new BarEntry(haveStudy,1));
		yVals.add(new BarEntry(needReview,2));
		yVals.add(new BarEntry(haveReview,3));
		
		BarDataSet set = new BarDataSet(yVals,"今日进度");
		set.setBarSpacePercent(35f);
		
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set);
		
		BarData data = new BarData(xVals,dataSets);
		data.setValueTextSize(13f);
		barchart.setData(data);
		
		Legend l = barchart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setForm(LegendForm.SQUARE);
		l.setFormSize(9f);
		l.setTextSize(11f);
		l.setXEntrySpace(4f);
	}
	
}
