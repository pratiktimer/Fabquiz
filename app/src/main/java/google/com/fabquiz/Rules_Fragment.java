package google.com.fabquiz;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import online.Category.GetCategory;


public class Rules_Fragment extends Fragment implements OnClickListener {

	private static View view;
	Button letsplay;
   	private static FragmentManager fragmentManager;
  	@Override
	public void onStart() {
		super.onStart();
	}
	public Rules_Fragment() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_rules, container, false);
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
		}
		initViews();
        setListeners();
		//((MainActivity)getActivity()).updateStatusBarColor("#010101");
		return view;
	}
	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		letsplay=(Button) view.findViewById(R.id.letsplay);
	}
	// Set Listeners
	private void setListeners() {
		letsplay.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case  R.id.letsplay:

				Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_up, R.anim.left_out).toBundle();
				Intent goonlinecategories = new Intent(getActivity(), GetCategory.class);
				goonlinecategories.putExtra("position", 1);
				goonlinecategories.putExtra("multiplayers", 0);
				try{
				startActivity( goonlinecategories, bundle);}
				catch (Exception e){}
				break;
		}

	}


}
