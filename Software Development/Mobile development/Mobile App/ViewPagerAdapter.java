package com.example.moobiledevelopmentca1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**

 Adapter class for managing fragments in a ViewPager2.
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    /**
     Constructor for the ViewPagerAdapter.
     @param fragmentActivity The FragmentActivity that will host the fragments.
     */
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     Creates a new fragment based on the position.
     @param position The position of the fragment in the ViewPager2.
     @return The fragment corresponding to the position.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Main_Fragment();
            case 1:
                return new Menu_Fragment();
            case 2:
                return new Specials_Fragment();
            case 3:
                return new Feedback_Fragment();
            default:
                return new Main_Fragment();
        }
    }

    /**

     Returns the number of fragments in the ViewPager2.
     @return The number of fragments.
     */
    @Override
    public int getItemCount() {
        return 4; // Number of fragments
    }

}