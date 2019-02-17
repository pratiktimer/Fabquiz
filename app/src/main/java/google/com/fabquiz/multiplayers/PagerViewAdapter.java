package google.com.fabquiz.multiplayers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Pratik on 3/11/2018.
 */

class PagerViewAdapter  extends FragmentPagerAdapter {
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               get_mult_result_frag p= get_mult_result_frag.newIstance();
                return p;
               // break;
            case 1:
                get_mult_result_received_frag p2=get_mult_result_received_frag.newIstance();
                return p2;

             //   break;
          default:  return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
