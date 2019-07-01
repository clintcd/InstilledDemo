package company.immersion.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;

/**
 * The FragmentPosition class holds all fragments with a particular starting position.  FragmentPosition objects
 * are created and held within FragmentGroup objects.
 */
public class FragmentPosition {

    /**
     * Comparator for sorting FragmentPositions.
     */
    private class FragmentSortComparator implements Comparator<Fragment> {

        /**
         * Compares fragments to sort in ascending order.
         *
         * @param a
         * @param b
         * @return
         */
        public int compare(Fragment a, Fragment b)
        {
            return a.getLength() > b.getLength() ? -1 : a.getLength() == b.getLength() ? 0 : 1;
        }
    }

    private FragmentGroup group = null;
    private ArrayList<Fragment> fragments;
    private String name;
    private FragmentPosition segmentEndFragmentPosition = null;
    private long position;
    private boolean synced = false;

    /**
     * Constructor for FragmentPosition.
     *
     * @param position
     */
    public FragmentPosition(long position) {
        fragments = new ArrayList<Fragment>();
        this.position = position;
        this.name = "Position " + position;
    }

    /**
     * Adds a fragment to the position.
     *
     * @param newFragment
     */
    public void addFragment(Fragment newFragment) {
        synced = false;
        fragments.add(newFragment);
    }


    /**
     * Returns all the fragments held for this position.
     *
     * @return
     */
    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    /**
     * Returns the FragmentGroup instance object this FragmentPosition instance is associated with.
     * @return
     */
    public FragmentGroup getGroup() {
        return group;
    }

    /**
     * Sets the group for this position.
     *
     * @param group
     */
    public void setGroup(FragmentGroup group) {
        this.group = group;
    }

    /**
     * Returns the name of this fragment position.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this fragment position.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the largest and last FragmentPosition in a group the longest fragment at this position extends into.
     *
     * @return
     */
    public FragmentPosition getSegmentEndFragmentPosition() {
        return segmentEndFragmentPosition;
    }

    /**
     * Sets the segment's end fragment position for this fragment position.
     *
     * @param segmentEndFragmentPosition
     */
    public void setSegmentEndFragmentPosition(FragmentPosition segmentEndFragmentPosition) {
        this.segmentEndFragmentPosition = segmentEndFragmentPosition;
    }

    /**
     * Gets the position as a long.
     * @return
     */
    public long getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     * Synchronizes the fragment position by sorting the fragments (high to low) and requesting that the parent
     * group synchronize itself as well.
     */
    protected void sync() {
        if (synced == false) {
            synced = true;
            // sort fragments (pos 0 = longest, last pos = shortest)
            Collections.sort(fragments, new FragmentSortComparator());
        }

        // sync the whole group so we can depend on data for uvt calculations, etc being correct
        if (group != null) {
            group.sync();
        }
    }

    /**
     * Determines which fragment position this one overlaps.
     */
    protected void calculateEndFragment() {
        sync();
        // We default to the current fragment position also being its own end fragment position
        setSegmentEndFragmentPosition(this);
        int segmentBeginPos = group.getArrayPosition(this.getLongestFragment());
        // loop through fragment positions to the right of this one until the furthest position is found
        for (int i = segmentBeginPos+1; i < group.getPositionCount(); i++) {
            FragmentPosition potentialEndFragmentPosition = group.getFragmentPosition(i);
            // So, if our position + our length extends past another position, that position is our potential end
            if ((this.getPosition() + this.getLongestFragment().getLength()) >= potentialEndFragmentPosition.getPosition()) {
                setSegmentEndFragmentPosition(potentialEndFragmentPosition);
            }
            // if we fell short of the last checked position, we're done.  We either have a "gap" or we're at the end
            else {
                break;
            }
        }
    }

    /**
     * Returns the longest fragment held at this position.
     *
     * @return
     */
    public Fragment getLongestFragment() {
        sync();
        if (fragments.isEmpty() == false) {
            return fragments.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the UVT for this position.
     *
     * @return
     */
    public long getPositionUniqueViewTime() {
        long uvt = 0;

        sync();

        if (getLongestFragment() != null) {
            uvt = getLongestFragment().getLength();
        }

        return uvt;
    }

    /**
     * Returns the UVT for the segment beginning at this position.
     *
     * @return
     */
    public long getSegmentUniqueViewTime() {
        long uvt = 0;

        sync();

        FragmentPosition a = this;
        FragmentPosition b = this.getSegmentEndFragmentPosition();

        if (a == b) {
            uvt = getLongestFragment().getLength();
        }
        else {
            long aPosition = a.getPosition();
            long aLength = a.getLongestFragment().getLength();
            long bPosition = b.getPosition();
            long bLength = b.getLongestFragment().getLength();

            long distanceAtoB = bPosition - aPosition;
            long distanceOfAPastB = aLength - distanceAtoB;

            long longestDistancePastB = Math.max(distanceOfAPastB, bLength);

            uvt =  distanceAtoB + longestDistancePastB;
        }

        return uvt;
    }
}
