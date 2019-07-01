package company.immersion.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FragmentPositionTest {

    private static final long POSITION = 10;
    private static final long LENGTH = 50;
    private static final long POSITION2 = 10;
    private static final long LENGTH2 = 100;
    private static final String NAME = "testName";
    private static final String NAME2 = "testName2";

    private FragmentPosition fragmentPosition = null;
    private FragmentGroup fragmentGroup = null;

    @BeforeEach
    void init() {
        fragmentPosition = new FragmentPosition(POSITION);
        fragmentGroup = new FragmentGroup();
        fragmentPosition.setGroup(fragmentGroup);
    }

    @Test
    void addFragment() {
        assertEquals(null, fragmentPosition.getLongestFragment());
        Fragment frag = new Fragment(POSITION, LENGTH);
        fragmentPosition.addFragment(frag);
        assertEquals(frag, fragmentPosition.getLongestFragment());
    }

    @Test
    void getFragments() {
        Fragment fragA = new Fragment(POSITION, LENGTH);
        fragmentPosition.addFragment(fragA);
        assertEquals(1, fragmentPosition.getFragments().size());
    }

    @Test
    void getAndSetGroup() {
        fragmentPosition.setGroup(null);
        assertEquals(null, fragmentPosition.getGroup());
        fragmentPosition.setGroup(fragmentGroup);
        assertEquals(fragmentGroup, fragmentPosition.getGroup());
    }

    @Test
    void getAndSetName() {
        fragmentPosition.setName(NAME);
        assertEquals(NAME, fragmentPosition.getName());
    }

    @Test
    void getAndSetSegmentEndFragmentPosition() {
        assertEquals(null, fragmentPosition.getSegmentEndFragmentPosition());
        FragmentPosition endPosition = new FragmentPosition(200);
        fragmentPosition.setSegmentEndFragmentPosition(endPosition);
        assertEquals(endPosition, fragmentPosition.getSegmentEndFragmentPosition());
    }

    @Test
    void getAndSetPosition() {
        fragmentPosition.setPosition(POSITION2);
        assertEquals(POSITION2, fragmentPosition.getPosition());
    }

    @Test
    void getLongestFragment() {
        Fragment fragA = new Fragment(POSITION, LENGTH);
        Fragment fragB = new Fragment(POSITION2, LENGTH2);

        fragmentPosition.addFragment(fragA);
        fragmentPosition.addFragment(fragB);

        assertEquals(fragB, fragmentPosition.getLongestFragment());
    }

    @Test
    void getPositionUniqueViewTime() {
        Fragment fragA = new Fragment(POSITION, LENGTH);
        Fragment fragB = new Fragment(POSITION2, LENGTH2);

        fragmentPosition.addFragment(fragA);
        fragmentPosition.addFragment(fragB);

        assertEquals(LENGTH2, fragmentPosition.getPositionUniqueViewTime());
    }
}