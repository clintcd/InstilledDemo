package company.immersion.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FragmentGroupTest {

    private static final long POSITION1 = 0;
    private static final long LENGTH1 = 1000;
    private static final long POSITION2 = 2000;
    private static final long LENGTH2 = 1000;
    private static final long POSITION3 = 2500;
    private static final long LENGTH3 = 1500;
    private static final String NAME = "testName";


    private FragmentGroup fragmentGroup = null;

    @BeforeEach
    void init() {
        fragmentGroup = new FragmentGroup();
    }

    @Test
    void addFragment() {
        assertEquals(0, fragmentGroup.getPositionCount());
        fragmentGroup.addFragment(new Fragment(POSITION1, LENGTH1));
        assertEquals(1, fragmentGroup.getPositionCount());
    }

    @Test
    void getArrayPosition() {
        Fragment frag1 = new Fragment(POSITION1, LENGTH1);
        Fragment frag2 = new Fragment(POSITION2, LENGTH2);
        fragmentGroup.addFragment(frag1);
        fragmentGroup.addFragment(frag2);

        assertEquals( 1, fragmentGroup.getArrayPosition(frag2));

    }

    @Test
    void getPositionCount() {
        Fragment frag1 = new Fragment(POSITION1, LENGTH1);
        Fragment frag2 = new Fragment(POSITION2, LENGTH2);
        fragmentGroup.addFragment(frag1);
        fragmentGroup.addFragment(frag2);

        assertEquals( 2, fragmentGroup.getPositionCount());
    }

    @Test
    void getFragmentPosition() {
        Fragment frag1 = new Fragment(POSITION1, LENGTH1);
        Fragment frag2 = new Fragment(POSITION2, LENGTH2);
        fragmentGroup.addFragment(frag1);
        fragmentGroup.addFragment(frag2);

        assertEquals( LENGTH2, fragmentGroup.getFragmentPosition(1).getLongestFragment().getLength());
    }

    @Test
    void getAndSetName() {
        fragmentGroup.setName(NAME);
        assertEquals(NAME, fragmentGroup.getName());
    }

    @Test
    void getUniqueSegmentFragmentPositions() {
        Fragment frag1 = new Fragment(POSITION1, LENGTH1);
        Fragment frag2 = new Fragment(POSITION2, LENGTH2);
        fragmentGroup.addFragment(frag1);
        fragmentGroup.addFragment(frag2);

        assertEquals( 2, fragmentGroup.getUniqueSegmentFragmentPositions().size());
    }

    @Test
    void getUniqueViewTime() {
        fragmentGroup.addFragment(new Fragment(POSITION1, LENGTH1));
        fragmentGroup.addFragment(new Fragment(POSITION2, LENGTH2));
        fragmentGroup.addFragment(new Fragment(POSITION3, LENGTH3));

        assertEquals(3000, fragmentGroup.getUniqueViewTime());
        assertEquals( 2, fragmentGroup.getUniqueSegmentFragmentPositions().size());
    }

    @Test
    void getUniqueViewTimeFromThreeDistinctSegments() {
        fragmentGroup.addFragment(new Fragment(POSITION1, LENGTH1));
        fragmentGroup.addFragment(new Fragment(POSITION2, LENGTH2));
        fragmentGroup.addFragment(new Fragment(POSITION3+1000, LENGTH3));

        assertEquals(3500, fragmentGroup.getUniqueViewTime());
        assertEquals( 3, fragmentGroup.getUniqueSegmentFragmentPositions().size());
    }
}