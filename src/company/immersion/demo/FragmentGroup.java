package company.immersion.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The FragmentGroup class holds a collection of fragments and returns information about the entire collection, such as
 * the unique view time of the entire group.
 */
public class FragmentGroup {

    /**
     * Comparator for sorting the fragment positions from low to high, based on position value;
     */
    private class FragmentGroupSortComparator implements Comparator<FragmentPosition> {

        /**
         * Compares two fragment positions.
         *
         * @param a
         * @param b
         * @return
         */
        public int compare(FragmentPosition a, FragmentPosition b)
        {
            return a.getPosition() < b.getPosition() ? -1 : a.getPosition() == b.getPosition() ? 0 : 1;
        }
    }


    private ArrayList<FragmentPosition> fragmentPositions;
    private HashMap<Long, Long> fragmentPositionLookup;  // position in timeline, position in fragment list
    private ArrayList<FragmentPosition> uniqueSegmentFragmentPositions;
    private String name;
    private boolean synced = false;

    /**
     * Constructor.
     */
    public FragmentGroup() {
        fragmentPositions = new ArrayList<FragmentPosition>();
        fragmentPositionLookup = new HashMap<Long, Long>();
        uniqueSegmentFragmentPositions = new ArrayList<FragmentPosition>();
        this.name = "Fragment Group";
    }

    /**
     * Adds a fragment to the group.
     *
     * @param newFragment
     */
    public void addFragment(Fragment newFragment) {
        synced = false;
        Long fragmentIndex = fragmentPositionLookup.get(Long.valueOf(newFragment.getPosition()));
        if (fragmentIndex == null) {
            FragmentPosition fragPos = new FragmentPosition(newFragment.getPosition());
            fragPos.setGroup(this);
            fragmentPositions.add(fragPos);
            fragmentPositionLookup.put(Long.valueOf(newFragment.getPosition()), Long.valueOf(fragmentPositions.size()-1));
            fragPos.addFragment(newFragment);
        }
    }

    /**
     * Returns the array position (an int) for a particular fragment.
     *
     * @param fragment
     * @return
     */
    public int getArrayPosition(Fragment fragment) {
        Long pos = this.fragmentPositionLookup.get(Long.valueOf(fragment.getPosition()));
        if (pos == null) {
            return -1;
        }
        else {
            return pos.intValue();
        }
    }

    /**
     * Returns the total number of unique positions holding fragments.
     *
     * @return
     */
    public int getPositionCount() {
        return fragmentPositions.size();
    }

    /**
     * Returns the fragment held at the index passed.
     *
     * @param index
     * @return
     */
    public FragmentPosition getFragmentPosition(int index) {
        return fragmentPositions.get(index);
    }

    /**
     * Gets the name of the group.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the group.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns an array list of the subset of fragment positions that are the start of unique segments of overlapping
     * fragments.
     *
     * @return
     */
    public ArrayList<FragmentPosition> getUniqueSegmentFragmentPositions() {
        sync();
        return uniqueSegmentFragmentPositions;
    }

    /**
     * Returns the unique view time for all the fragments held in the group.
     *
     * @return
     */
    public long getUniqueViewTime() {
        long uvt = 0;
        sync();

        // Add up all the segments to determine total uvt for all segments
        // find the end fragment position each max fragment extends into
        for (int i = 0; i < uniqueSegmentFragmentPositions.size(); i++) {
            uvt = uvt + uniqueSegmentFragmentPositions.get(i).getSegmentUniqueViewTime();
        }

        return uvt;
    }


    /**
     * Synchronizes the fragment group by ordering the positions, refreshing an lookup index, reordering fragments
     * at unique FragmentPositions, and determining the unique segment ranges for overlapping fragments.
     */
    protected void sync() {
        if (synced == false) {
            // We'll be all synced in a moment!
            synced = true;

            // sort the fragment positions
            Collections.sort(fragmentPositions, new FragmentGroupSortComparator());
            // sort each fragment position
            for (int i = 0; i < fragmentPositions.size(); i++) {
                fragmentPositions.get(i).sync();
            }
            // Refresh the fragment position lookup map
            fragmentPositionLookup.clear();
            for (int i = 0; i < fragmentPositions.size(); i++) {
                fragmentPositionLookup.put(Long.valueOf(fragmentPositions.get(i).getPosition()), Long.valueOf(i));
            }
            // find the end fragment position each max fragment extends into
            for (int i = 0; i < fragmentPositions.size(); i++) {
                fragmentPositions.get(i).calculateEndFragment();
            }
            // determine the segments of the group
            uniqueSegmentFragmentPositions.clear();
            for (int i = 0; i < fragmentPositions.size(); i++) {
                // Get segment start, add to list
                FragmentPosition fragPos = fragmentPositions.get(i);
                uniqueSegmentFragmentPositions.add(fragPos);
                // Now set position to end of segment to find the next segment
                i = this.getArrayPosition(fragPos.getSegmentEndFragmentPosition().getLongestFragment());
            }
        }
    }
}
